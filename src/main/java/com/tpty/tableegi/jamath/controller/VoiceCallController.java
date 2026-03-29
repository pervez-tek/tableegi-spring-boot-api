package com.tpty.tableegi.jamath.controller;

import com.tpty.tableegi.jamath.dto.TwilioRequest;
import com.twilio.rest.api.v2010.account.Call;

import com.twilio.type.PhoneNumber;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/call")
public class VoiceCallController {

    @PostMapping("/voiceCall")
    public ResponseEntity<String> makeVoiceCall(@RequestBody TwilioRequest twilioRequest) throws Exception {

        // Check if RequestBody has valid data or NOT
        // In this example, the message field doesn't have to be checked
        if (twilioRequest == null || twilioRequest.getFromPhoneNumber() == null
                || twilioRequest.getToPhoneNumber() == null) {
            return ResponseEntity.badRequest().body("Invalid request data");
        }

        // Extract Request Data
        String fromNumber = twilioRequest.getFromPhoneNumber();
        String toNumber = twilioRequest.getToPhoneNumber();

        // Make a call
        Call.creator(new PhoneNumber(fromNumber), new PhoneNumber(toNumber),
                new URI("https://demo.twilio.com:443/docs/voice.xml")).create();
        return ResponseEntity.ok("Call made Succesfully !");
    }
}
