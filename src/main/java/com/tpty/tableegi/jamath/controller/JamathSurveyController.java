package com.tpty.tableegi.jamath.controller;

import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.dto.NotificationEvent;
import com.tpty.tableegi.jamath.entity.JamathActivityLogSurveyEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.JamathSurveyService;
import com.tpty.tableegi.jamath.service.JamathiActivityLogService;
import com.tpty.tableegi.jamath.service.NotificationPublisher;
import com.tpty.tableegi.jamath.utils.JamathSurveyDtoConverterToEntity;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class JamathSurveyController {

    private JamathSurveyService jamathSurveyService;

    private JamathiActivityLogService jamathiActivityLogService;

    private NotificationPublisher notificationPublisher;


    public JamathSurveyController(JamathSurveyService jamathSurveyService,
                                  JamathiActivityLogService jamathiActivityLogService,
                                  NotificationPublisher notificationPublisher) {
        this.jamathSurveyService = jamathSurveyService;
        this.jamathiActivityLogService = jamathiActivityLogService;
        this.notificationPublisher = notificationPublisher;
    }

    @PostMapping("/submitSurveyJamath")
    public ResponseEntity<String> submitJamathSurvey(@RequestBody JamathSurveryDto jamathSurveryDto)
            throws InvalidDataException, MessagingException, IOException {
        log.info("submitJamathSurvey started");
        System.out.println(jamathSurveryDto);
        JamathMasterSurveyEntity jamathMasterSurveyEntity = jamathSurveyService.submitJamathSurvey(jamathSurveryDto);
        if (Objects.nonNull(jamathMasterSurveyEntity)) {
            jamathSurveryDto.setNamaziId(jamathMasterSurveyEntity.getId().toString());
            jamathSurveryDto.setMasjidId(jamathMasterSurveyEntity.getMasjidId().toString());
        }
        JamathActivityLogSurveyEntity jamathActivityLogSurveyEntity =
                jamathiActivityLogService.submitJamathActivityLogSurvey(jamathSurveryDto);

        if (jamathActivityLogSurveyEntity == null || jamathMasterSurveyEntity == null) {
            throw new InvalidDataException("Please contact Admin");
        }

        if (jamathMasterSurveyEntity.getId() != null) {

            notificationPublisher.publishAsync(
                    NotificationEvent.builder().
                            name(jamathMasterSurveyEntity.getName()).
                            email(jamathMasterSurveyEntity.getEmail()).
                            phone(jamathMasterSurveyEntity.getPhone()).
                            subject("Welcome To Our App").
                            message("Welcome to Our App").
                            build());
            log.info("submitJamathSurvey Update Completed");
            return ResponseEntity.ok("Successfully updated in the database..");
        } else {
            log.info("submitJamathSurvey Adding new one Completed");
            return ResponseEntity.ok("Successfully added in the database..");
        }
    }

    @PostMapping("/getJamathiDetails")
    public ResponseEntity<JamathSurveryDto> getJamathiDetails(@RequestBody JamathSurveryDto jamathSurveryDto) throws InvalidDataException {

        System.out.println(jamathSurveryDto);
        JamathMasterSurveyEntity jamathMasterSurveyEntity = jamathSurveyService.findByEmail(jamathSurveryDto.getEmail());
        if (Objects.nonNull(jamathMasterSurveyEntity) && Objects.nonNull(jamathMasterSurveyEntity.getId())) {
            jamathSurveryDto = JamathSurveyDtoConverterToEntity.toDTO(jamathMasterSurveyEntity);
            return ResponseEntity.ok(jamathSurveryDto);
        }
        jamathSurveryDto.setEmail(null);

        return ResponseEntity.ok(jamathSurveryDto);
    }
}
