package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.repo.JamathMasterSurveyRepo;
import com.tpty.tableegi.jamath.utils.AdminDtoConverterToEntity;
import com.tpty.tableegi.jamath.utils.JamathSurveyDtoConverterToEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class JamathSurveyServiceImpl implements JamathSurveyService {

    private JamathMasterSurveyRepo jamathMasterSurveyRepo;
    private ProfileImageService profileImageService;

    public JamathSurveyServiceImpl(JamathMasterSurveyRepo jamathMasterSurveyRepo, ProfileImageService profileImageService) {
        this.jamathMasterSurveyRepo = jamathMasterSurveyRepo;
        this.profileImageService = profileImageService;
    }

    @Override
    public JamathMasterSurveyEntity submitJamathSurvey(JamathSurveryDto jamathSurveryDto) throws InvalidDataException, IOException {


        JamathMasterSurveyEntity jamathMasterSurveyEntity = null;
        if (Objects.nonNull(jamathSurveryDto) && jamathSurveryDto.getId() != null && !jamathSurveryDto.getId().isBlank()) {

            jamathMasterSurveyEntity = jamathMasterSurveyRepo.findById(UUID.fromString(jamathSurveryDto.getId())).
                    orElseThrow(() -> new InvalidDataException("No JamathMasterSurveyEntity found for id: " + jamathSurveryDto.getId()));
            if (Objects.nonNull(jamathMasterSurveyEntity) && Objects.nonNull(jamathMasterSurveyEntity.getId())) {
                JamathSurveyDtoConverterToEntity.updateEntity(jamathMasterSurveyEntity, jamathSurveryDto);
                jamathMasterSurveyEntity.setLastUpdateDate(LocalDateTime.now());
                //jamathMasterSurveyEntity.setProfileImage(profileImageService.getImageBytes(jamathMasterSurveyEntity.getImage()));
            }
        } else {
            // Convert back
            jamathMasterSurveyEntity = JamathSurveyDtoConverterToEntity.toEntity(jamathSurveryDto);
        }

        return jamathMasterSurveyRepo.save(jamathMasterSurveyEntity);
    }

    @Override
    public JamathMasterSurveyEntity findByEmail(String email) throws InvalidDataException {
        return jamathMasterSurveyRepo.findByEmail(email).orElse(null);
    }
}
