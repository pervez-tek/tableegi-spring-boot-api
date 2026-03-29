package com.tpty.tableegi.jamath.config;

import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
@Slf4j
public class TableegiTwillioConfiguration {

    // Twilio Credentials
    @Value("${twilio.account.id}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth.id}")
    private String AUTH_ID;


    @PostConstruct
    public void initTwillio() {
        // +18326696475

        // Initializing the Twilio
        // Java Library with our credentials

        new File("/app/logs").mkdirs();
        new File("/app/uploads/profiles").mkdirs();

        log.info("Twillio initiation done");
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }
}
