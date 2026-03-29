package com.tpty.tableegi.jamath.controller;

import com.tpty.tableegi.jamath.dto.EmailDto;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.TableegiEmailService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tableegi")
@Slf4j
public class TableegiEmailController {

    @Autowired
    private TableegiEmailService emailService;

    @PostMapping("/email/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDto emailDto) throws MessagingException, InvalidDataException {

        emailService.sendSimpleMail(emailDto);
        log.info("sendEmail Completed");
        return ResponseEntity.ok("Email sent successfully!");
    }

    @PostMapping("/email/sendWithAttachment")
    public ResponseEntity<String> sendEmailWithAttachment(@RequestBody EmailDto emailDto) throws MessagingException {

        emailService.sendEmailWithAttachment(emailDto);
        log.info("sendEmailWithAttachment Completed");
        return ResponseEntity.ok("Email with attachment sent successfully!");
    }
}

