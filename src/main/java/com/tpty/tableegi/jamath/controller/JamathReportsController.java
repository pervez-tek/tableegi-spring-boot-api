package com.tpty.tableegi.jamath.controller;


import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.dto.JamathiActivityLogResponseDto;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.service.JamathSurveyService;
import com.tpty.tableegi.jamath.service.JamathiActivityLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/tableegi")
@CrossOrigin(origins = "${front.end.react.api.url}") // allow React dev server
@Slf4j
public class JamathReportsController {

    private JamathSurveyService jamathSurveyService;

    private JamathiActivityLogService jamathiActivityLogService;

    public JamathReportsController(JamathSurveyService jamathSurveyService, JamathiActivityLogService jamathiActivityLogService) {
        this.jamathSurveyService = jamathSurveyService;
        this.jamathiActivityLogService = jamathiActivityLogService;
    }

    @GetMapping("/betweenJamathDates")
    public ResponseEntity<List<JamathiActivityLogResponseDto>>
    fetchJamathiDataBetween(@RequestParam("fromDate") LocalDate fromDate,
                            @RequestParam("toDate") LocalDate toDate) throws InvalidDataException {

        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromDate cannot be after toDate");
        }
        log.info("fetchJamathiDataBetween fromDate:{} & toDate:{} Completed", fmt(fromDate), fmt(toDate));
        return ResponseEntity.ok(jamathiActivityLogService.jamathiDatesRangeBetween(fromDate, toDate));
    }

    private String fmt(LocalDate dt) {
        return dt == null ? "null" : dt.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
}

