
package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.EmailDto;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;

@Service
public class TableegiEmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.smtp.from.email}")
    private String SMTP_EMAIL_FROM;

    // Send HTML Welcome Formated Mail email
    public void sendHtmlFormatedWelcomeEmail(EmailDto emailDto) throws MessagingException {
        try {

            validateDto(emailDto);

            Context context = new Context();
            context.setVariable("title", emailDto.getSubject());
            context.setVariable("name", emailDto.getUser());
            context.setVariable("message", emailDto.getBody());
            context.setVariable("registrationLink", emailDto.getLink());

            String htmlContent = templateEngine.process("emailTemplate", context);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(SMTP_EMAIL_FROM);
            helper.setTo(emailDto.getTo());

            helper.setSubject(emailDto.getSubject());
            helper.setText(htmlContent, true); // true = HTML content

            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

    private static void validateDto(EmailDto emailDto) throws InvalidDataException {
        // Check if RequestBody has valid data or NOT
        if (emailDto == null || emailDto.getSubject() == null
                || emailDto.getUser() == null || emailDto.getBody() == null) {
            throw new InvalidDataException("Invalid request data");
        }

        if (emailDto.getSubject().trim().equalsIgnoreCase("")
                || emailDto.getUser().trim().equalsIgnoreCase("")
                || emailDto.getBody().trim().equalsIgnoreCase("")) {
            throw new InvalidDataException("Invalid request data");
        }
    }

    // Send email with attachment
    public void sendEmailWithAttachment(EmailDto emailDto) throws MessagingException {
        try {
            validateDto(emailDto);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(SMTP_EMAIL_FROM);
            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getBody(), true); // true = HTML content

            FileSystemResource file = new FileSystemResource(new File(emailDto.getAttachmentPath()));
            helper.addAttachment(file.getFilename(), file);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendSimpleMail(EmailDto emailDto) throws MessagingException {
        try {
            validateDto(emailDto);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(SMTP_EMAIL_FROM);
            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getBody(), true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendHtmlMessage(EmailDto emailDto) throws MessagingException {
        try {
            validateDto(emailDto);

            // Wrap plain text in minimal HTML
            String htmlContent = "<html><body style='font-family:Arial,sans-serif;'>" +
                    "<pre style='white-space:pre-wrap; font-size:14px;'>" +
                    emailDto.getBody() +
                    "</pre>" +
                    "</body></html>";

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(SMTP_EMAIL_FROM);
            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(htmlContent, true); // true = HTML

            mailSender.send(message);
        } catch (MessagingException e) {
            throw e;
        } catch (InvalidDataException e) {
            throw new RuntimeException(e);
        }
    }

}

