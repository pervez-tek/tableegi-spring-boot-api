package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.entity.JamathActivityLogSurveyEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JamathSurveyActivityLogDtoConverterToEntity {

    // Entity -> DTO
    public static JamathSurveryDto toDTO(JamathActivityLogSurveyEntity entity) {
        if (entity == null) return null;

        return JamathSurveryDto.builder().
                id(entity.getId() != null ? entity.getId().toString() : null).
                comment(entity.getComments()).

                masjidId(entity.getMasjidId() != null ? entity.getMasjidId().toString() : null).
                namaziId(entity.getNamaziId() != null ? entity.getNamaziId().toString() : null).
                build();
    }

    // DTO -> Entity
    public static JamathActivityLogSurveyEntity toEntity(JamathSurveryDto dto) {
        if (dto == null) return null;

        return JamathActivityLogSurveyEntity.builder()
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
                .masjidId(UUID.fromString(dto.getMasjidId()))
                .comments(dto.getComment())
                .namaziId(UUID.fromString(dto.getNamaziId()))
                .build();
    }

    // List<Entity> -> List<DTO>
    public static List<JamathSurveryDto> toDTOList(List<JamathActivityLogSurveyEntity> entities) {
        return entities.stream().map(JamathSurveyActivityLogDtoConverterToEntity::toDTO).collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<JamathActivityLogSurveyEntity> toEntityList(List<JamathSurveryDto> dtos) {
        return dtos.stream().map(JamathSurveyActivityLogDtoConverterToEntity::toEntity).collect(Collectors.toList());
    }

    public static void updateEntity(JamathActivityLogSurveyEntity entity, JamathSurveryDto dto) {
        entity.setComments(dto.getComment());
        entity.setNamaziId(UUID.fromString(dto.getNamaziId()));
        entity.setMasjidId(UUID.fromString(dto.getMasjidId()));
    }
}
