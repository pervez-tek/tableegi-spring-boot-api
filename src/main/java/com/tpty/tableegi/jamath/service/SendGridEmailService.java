package com.tpty.tableegi.jamath.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.tpty.tableegi.jamath.dto.EmailDto;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;

@Service
public class SendGridEmailService {

    @Value("${spring.mail.password}")
    private String apiKey;

    @Value("${spring.smtp.from.email}")
    private String fromEmail;

    @Autowired
    private TemplateEngine templateEngine;

    @Retryable(retryFor = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000))
    public void sendEmail(EmailDto emailDto) throws Exception {
        validateDto(emailDto);
        Email from = new Email(fromEmail);
        Email toEmail = new Email(emailDto.getTo());
        Content content = new Content("text/plain", emailDto.getBody());

        Mail mail = new Mail(from, emailDto.getSubject(), toEmail, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(mail.build());

        Response response = sg.api(request);

        if (response.getStatusCode() >= 400) {
            throw new RuntimeException("SendGrid failed: " + response.getBody());
        }
    }

    private static void validateDto(EmailDto emailDto) throws InvalidDataException {
        // Check if RequestBody has valid data or NOT
        if (emailDto == null || emailDto.getSubject() == null || emailDto.getUser() == null || emailDto.getBody() == null) {
            throw new InvalidDataException("Invalid request data");
        }

        if (emailDto.getSubject().trim().equalsIgnoreCase("") || emailDto.getUser().trim().equalsIgnoreCase("") || emailDto.getBody().trim().equalsIgnoreCase("")) {
            throw new InvalidDataException("Invalid request data");
        }
    }

    public void sendHtmlTemplateEmail(EmailDto emailDto) {
        try {
            validateDto(emailDto);

            Context context = new Context();
            context.setVariable("title", emailDto.getSubject());
            context.setVariable("name", emailDto.getUser());
            context.setVariable("message", emailDto.getBody());
            context.setVariable("registrationLink", emailDto.getLink());

            String htmlBody = templateEngine.process("emailTemplate", context);

            Email from = new Email(fromEmail);
            Email toEmail = new Email(emailDto.getTo());

            Content content = new Content("text/html", htmlBody);
            Mail mail = new Mail(from, emailDto.getSubject(), toEmail, content);

            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid failed: " + response.getBody());
            }
        } catch (InvalidDataException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlMessage(EmailDto emailDto) throws MessagingException {
        try {
            validateDto(emailDto);

            String htmlContent = "<html><body style='font-family:Arial,sans-serif;'>" +
                    "<pre style='white-space:pre-wrap; font-size:14px;'>" +
                    emailDto.getBody() +
                    "</pre>" +
                    "</body></html>";

            Email from = new Email(fromEmail);
            Email toEmail = new Email(emailDto.getTo());
            Content content = new Content("text/html", htmlContent);

            Mail mail = new Mail(from, emailDto.getSubject(), toEmail, content);

            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            if (response.getStatusCode() >= 400) {
                throw new RuntimeException("SendGrid failed: " + response.getBody());
            }
            // Wrap plain text in minimal HTML

        } catch (InvalidDataException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
