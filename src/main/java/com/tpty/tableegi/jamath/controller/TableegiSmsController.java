package com.tpty.tableegi.jamath.controller;


import com.tpty.tableegi.jamath.dto.TwilioRequest;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.TableegiTwillioNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tableegi")
@Slf4j
public class TableegiSmsController {

    private TableegiTwillioNotificationService twillioNotificationService;

    public TableegiSmsController(TableegiTwillioNotificationService twillioNotificationService) {
        this.twillioNotificationService = twillioNotificationService;
    }

    @PostMapping("/send/sms")
    public ResponseEntity<String> sendSms(@RequestBody TwilioRequest twilioRequest) throws InvalidDataException {
        String message = twillioNotificationService.sendSms(twilioRequest);
        log.info("sendSms Completed");
        return ResponseEntity.ok(message);
    }

    @PostMapping("/send/whatsapp")
    public ResponseEntity<String> sendWhatsApp(@RequestBody TwilioRequest twilioRequest) throws InvalidDataException {
        String message = twillioNotificationService.sendWhatsApp(twilioRequest);
        log.info("sendWhatsApp Completed");
        return ResponseEntity.ok(message);
    }
}
