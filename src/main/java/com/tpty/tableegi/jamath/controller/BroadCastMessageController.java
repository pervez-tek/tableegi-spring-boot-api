package com.tpty.tableegi.jamath.controller;

import com.tpty.tableegi.jamath.dto.BroadCastMessageDto;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.AdminLoginService;
import com.tpty.tableegi.jamath.service.BroadCastMessageService;
import com.tpty.tableegi.jamath.service.ProfileImageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class BroadCastMessageController {

    @Value("${tableegi.publish.whatsapp.notification}")
    private boolean PUBLISH_WHATSAPP_NOTIFICATION;

    @Value("${tableegi.publish.sms.notification}")
    private boolean PUBLISH_SMS_NOTIFICATION;

    @Value("${tableegi.publish.email.notification}")
    private boolean PUBLISH_EMAIL_NOTIFICATION;

    private final BroadCastMessageService broadCastMessageService;
    private final ProfileImageService profileImageService;

    private BroadCastMessageController(BroadCastMessageService broadCastMessageService, ProfileImageService profileImageService) {
        this.broadCastMessageService = broadCastMessageService;
        this.profileImageService = profileImageService;
    }

    @PostMapping("/broadCastMessage")
    public ResponseEntity<Object> publishMessage(@Valid @RequestBody BroadCastMessageDto messageDto) throws InvalidDataException {
        broadCastMessageService.publishMessage(messageDto);
        log.info("Broadcast message published");
        return ResponseEntity.ok("Message Delivered");
    }

}
