package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.TwilioRequest;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class TableegiTwillioNotificationService {

    public String sendSms(TwilioRequest twilioRequest)  throws InvalidDataException{

        // Check if RequestBody has valid data or NOT
        if (twilioRequest == null || twilioRequest.getFromPhoneNumber() == null
                || twilioRequest.getToPhoneNumber() == null || twilioRequest.getMessage() == null) {
            throw new InvalidDataException("Invalid request data");
        }

        if (twilioRequest.getFromPhoneNumber().trim().equalsIgnoreCase("")
                || twilioRequest.getMessage().trim().equalsIgnoreCase("")) {
            throw new InvalidDataException("Invalid request data");
        }

        // Extract Request Data
        String fromNumber = twilioRequest.getFromPhoneNumber();
        String toNumber = twilioRequest.getToPhoneNumber();
        String msg = twilioRequest.getMessage();

        // Create Message to be sent
        Message.creator(new PhoneNumber(toNumber), new PhoneNumber(fromNumber),
                msg).create();

        return "SMS sent Succesfully !";
    }

    public String sendWhatsApp(TwilioRequest twilioRequest) throws InvalidDataException {

        // Check if RequestBody has valid data or NOT
        if (twilioRequest == null || twilioRequest.getFromPhoneNumber() == null
                || twilioRequest.getToPhoneNumber() == null || twilioRequest.getMessage() == null) {
            throw new InvalidDataException("Invalid request data");
        }

        if (twilioRequest.getFromPhoneNumber().trim().equalsIgnoreCase("")
                || twilioRequest.getMessage().trim().equalsIgnoreCase("")) {
            throw new InvalidDataException("Invalid request data");
        }

        // Extract Request Data
        String fromNumber = twilioRequest.getFromPhoneNumber();
        String toNumber = twilioRequest.getToPhoneNumber();
        String msg = twilioRequest.getMessage();

        // Create Message to be sent
        Message.creator(new PhoneNumber("whatsapp:" + toNumber), new PhoneNumber("whatsapp:" + fromNumber),
                msg).create();

        return "Whatsapp Message sent Succesfully !";
    }
}
