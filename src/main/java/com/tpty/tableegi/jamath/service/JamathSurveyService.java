package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;

import java.io.IOException;

public interface JamathSurveyService {

    JamathMasterSurveyEntity submitJamathSurvey(JamathSurveryDto jamathSurveryDto) throws InvalidDataException, IOException;

    JamathMasterSurveyEntity findByEmail(String email) throws InvalidDataException;
}
