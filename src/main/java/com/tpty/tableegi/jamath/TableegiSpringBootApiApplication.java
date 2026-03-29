package com.tpty.tableegi.jamath;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableJpaAuditing // ✅ enables auditing
@EnableScheduling
@EnableAsync
@Slf4j
public class TableegiSpringBootApiApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(TableegiSpringBootApiApplication.class);
        app.setBannerMode(Banner.Mode.LOG);
        app.run(args);
        log.info("Application started");
        log.debug("Application started");
        log.trace("Application started");

        log.warn("Something looks off");
        log.error("Something broke");
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
