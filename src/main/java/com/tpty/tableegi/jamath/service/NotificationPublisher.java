package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.EmailDto;
import com.tpty.tableegi.jamath.dto.NotificationDeliveryResult;
import com.tpty.tableegi.jamath.dto.NotificationEvent;
import com.tpty.tableegi.jamath.dto.TwilioRequest;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.utils.NotificationChannel;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class NotificationPublisher {

    @Value("${twilio.whatsapp.from.number}")
    private String TWILIO_WHATSAPP_FROM_NUMBER;

    @Value("${twilio.sms.from.number}")
    private String TWILIO_SMS_FROM_NUMBER;

    @Value("${tableegi.publish.whatsapp.notification}")
    private boolean PUBLISH_WHATSAPP_NOTIFICATION;

    @Value("${tableegi.publish.sms.notification}")
    private boolean PUBLISH_SMS_NOTIFICATION;

    @Value("${tableegi.publish.email.notification}")
    private boolean PUBLISH_EMAIL_NOTIFICATION;

    @Autowired
    private SendGridEmailService sendGridEmailService;


    @Autowired
    private TableegiEmailService tableegiEmailService;

    @Autowired
    private TableegiTwillioNotificationService notificationService;

    @Value("${twilio.whatsapp.link}")
    private String TWILIO_WHATSAPP_LINK;

    List<NotificationDeliveryResult> notificationResults = new ArrayList<>();

    @Async
    public void publishAsync(NotificationEvent req) throws MessagingException, InvalidDataException {
        log.info("Welcome Notification Service Async started");
        if (PUBLISH_EMAIL_NOTIFICATION) {
            sendGridEmailService.sendHtmlTemplateEmail(EmailDto.builder().
                    subject(req.getSubject()).
                    to(req.getEmail()).
                    body("Welcome").
                    user(req.getName()).
                    link(TWILIO_WHATSAPP_LINK).
                    build());

            //tableegiEmailService.sendHtmlFormatedWelcomeEmail

            notificationResults.add(NotificationDeliveryResult.builder()
                    .source(req.getEmail()).channel(NotificationChannel.EMAIL).success(true)
                    .build());
            log.info("Welcome Email Delivered");
        }

        numberNotification(req);
        log.info("Welcome Notification Service Async Completed");
    }

    private void numberNotification(NotificationEvent req) {
        try {
            if (PUBLISH_SMS_NOTIFICATION) {
                notificationService.sendSms(TwilioRequest.builder().
                        fromPhoneNumber(TWILIO_SMS_FROM_NUMBER).
                        toPhoneNumber("+91" + req.getPhone()).
                        message(req.getMessage()).
                        build());
                notificationResults.add(NotificationDeliveryResult.builder()
                        .source(req.getPhone()).channel(NotificationChannel.SMS).success(true)
                        .build());
                log.info("Welcome Sms Delivered");
            }
        } catch (InvalidDataException e) {
            //throw new RuntimeException(e);
            notificationResults.add(NotificationDeliveryResult.builder()
                    .source(req.getPhone()).channel(NotificationChannel.SMS).success(false)
                    .build());
        }

        try {
            if (PUBLISH_WHATSAPP_NOTIFICATION) {
                notificationService.sendWhatsApp(TwilioRequest.builder().
                        fromPhoneNumber(TWILIO_WHATSAPP_FROM_NUMBER).
                        toPhoneNumber("+91" + req.getPhone()).
                        message(req.getMessage()).
                        build());
                notificationResults.add(NotificationDeliveryResult.builder()
                        .source(req.getPhone()).channel(NotificationChannel.WHATSAPP).success(true)
                        .build());
                log.debug("Welcome Whatsapp Delivered");
            }
        } catch (InvalidDataException e) {
            notificationResults.add(NotificationDeliveryResult.builder()
                    .source(req.getPhone()).channel(NotificationChannel.WHATSAPP).success(false)
                    .build());
            // throw new RuntimeException(e);
        }

    }

    @Async
    public CompletableFuture<List<NotificationDeliveryResult>> publishBroadCastAsync(NotificationEvent req) {
        try {
            log.info("Broadcast Notification Service Async started");
            if (PUBLISH_EMAIL_NOTIFICATION) {
                sendGridEmailService.sendHtmlMessage(EmailDto.builder().
                        subject(req.getSubject()).
                        to(req.getEmail()).
                        body(req.getMessage()).
                        user(req.getName()).
                        link(TWILIO_WHATSAPP_LINK).
                        build());
                notificationResults.add(NotificationDeliveryResult.builder()
                        .source(req.getEmail()).channel(NotificationChannel.EMAIL).success(true)
                        .build());
                log.info("Broadcast Email Delivered");
            }
        } catch (MessagingException e) {
//            throw new RuntimeException(e);
            notificationResults.add(NotificationDeliveryResult.builder()
                    .source(req.getEmail()).channel(NotificationChannel.EMAIL).success(false)
                    .build());
        }

        numberNotification(req);
        log.info("Broadcast Notification Service Async Completed");
        return CompletableFuture.completedFuture(notificationResults);
    }
}
