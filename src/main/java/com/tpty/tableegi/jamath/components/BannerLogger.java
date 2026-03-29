package com.tpty.tableegi.jamath.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class BannerLogger {
    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        try (InputStream is = getClass().getResourceAsStream("/banner.txt")) {
            if (is != null) {
                String banner = new String(is.readAllBytes(), StandardCharsets.UTF_8);
                log.info("\n{}", banner);
            }
        } catch (IOException e) {
            log.warn("Unable to read banner.txt", e);
        }
    }
}

