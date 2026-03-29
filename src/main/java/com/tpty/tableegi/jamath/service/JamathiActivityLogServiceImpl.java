package com.tpty.tableegi.jamath.service;

import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.dto.JamathiActivityLogResponseDto;
import com.tpty.tableegi.jamath.entity.JamathActivityLogSurveyEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;
import com.tpty.tableegi.jamath.exceptions.InvalidDataException;
import com.tpty.tableegi.jamath.repo.JamathMasterSurveyRepo;
import com.tpty.tableegi.jamath.repo.JamathiActivityLogRepo;
import com.tpty.tableegi.jamath.utils.JamathSurveyActivityLogDtoConverterToEntity;
import com.tpty.tableegi.jamath.utils.JamathSurveyDtoConverterToEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JamathiActivityLogServiceImpl implements JamathiActivityLogService {

    private JamathiActivityLogRepo jamathiActivityLogRepo;

    public JamathiActivityLogServiceImpl(JamathiActivityLogRepo jamathiActivityLogRepo) {
        this.jamathiActivityLogRepo = jamathiActivityLogRepo;
    }

    @Override
    public JamathActivityLogSurveyEntity submitJamathActivityLogSurvey(JamathSurveryDto jamathSurveryDto) throws InvalidDataException {
        JamathActivityLogSurveyEntity jamathActivityLogSurveyEntity = null;
        if (Objects.nonNull(jamathSurveryDto) && jamathSurveryDto.getNamaziId() != null && !jamathSurveryDto.getNamaziId().isBlank()) {
            // Convert back
            LocalDate today = LocalDate.now();
            Optional<JamathActivityLogSurveyEntity> existing =
                    jamathiActivityLogRepo.findByNamaziIdAndActivityDate(UUID.fromString(jamathSurveryDto.getNamaziId()), today);
            if (existing.isPresent()) {
                JamathActivityLogSurveyEntity record = existing.get();
                record.setLastUpdateDate(LocalDateTime.now()); // update other details if needed return repo.save(record); } else {
                // Convert back
                JamathSurveyActivityLogDtoConverterToEntity.updateEntity(record, jamathSurveryDto);
                return jamathiActivityLogRepo.save(record);
            } else {
                // Convert back
                jamathSurveryDto.setId(null);
                jamathActivityLogSurveyEntity = JamathSurveyActivityLogDtoConverterToEntity.toEntity(jamathSurveryDto);
                return jamathiActivityLogRepo.save(jamathActivityLogSurveyEntity);
            }

        }

        return null;
    }

    @Override
    public List<JamathiActivityLogResponseDto> jamathiDatesRangeBetween(
            LocalDate fromDate,
            LocalDate toDate
    ) {
        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.plusDays(1).atStartOfDay();

        return jamathiActivityLogRepo
                .findJamathiDataBetweenDatesRange(fromDateTime, toDateTime)
                .stream()
                .map(row -> {
                    int halkaNo = ((Number) row[0]).intValue();
                    String masjidName = (String) row[1];
                    JamathMasterSurveyEntity master = (JamathMasterSurveyEntity) row[2];
                    JamathActivityLogSurveyEntity activity = (JamathActivityLogSurveyEntity) row[3];

                    return JamathiActivityLogResponseDto.builder()
                            .halkaNo(halkaNo)
                            .masjidName(masjidName)
                            .comment(master.getComment())
                            .name(master.getName())
                            .email(master.getEmail())
                            .service(master.getService())
                            .phone(master.getPhone())
                            .age(master.getAge())
                            .gender(master.getGender())
                            .comments(activity.getComments())
                            .image(master.getImage())
                            ._5aamal(
                                    Arrays.stream(master.get_5aamal().split(","))
                                            .map(s -> s.isBlank() ? -1 : Integer.parseInt(s))
                                            .collect(Collectors.toList())
                            )
                            .jamath(
                                    Arrays.stream(master.getJamath().split(","))
                                            .map(s -> s.isBlank() ? -1 : Integer.parseInt(s))
                                            .collect(Collectors.toList())
                            )
                            .build();
                })
                .toList();
    }

}
