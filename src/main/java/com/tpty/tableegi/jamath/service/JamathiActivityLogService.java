package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.dto.JamathiActivityLogResponseDto;
import com.tpty.tableegi.jamath.entity.JamathActivityLogSurveyEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;

import java.time.LocalDate;
import java.util.List;

public interface JamathiActivityLogService {

    JamathActivityLogSurveyEntity
    submitJamathActivityLogSurvey(JamathSurveryDto jamathSurveryDto) throws InvalidDataException;

    List<JamathiActivityLogResponseDto> jamathiDatesRangeBetween(LocalDate fromDate, LocalDate toDate);
}
