package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JamathSurveyDtoConverterToEntity {

    // Entity -> DTO
    public static JamathSurveryDto toDTO(JamathMasterSurveyEntity entity) {
        if (entity == null) return null;

        return JamathSurveryDto.builder().
                id(entity.getId() != null ? entity.getId().toString() : null).
                name(entity.getName()).email(entity.getEmail()).
                image(entity.getImage()).
                gender(entity.getGender()).
                phone(entity.getPhone()).age(entity.getAge()).
                service(entity.getService()).comment(entity.getComment()).
                agreeTerms(entity.isAgreeTerms()).masjidId(entity.getMasjidId().toString()).
                _5aamal(Arrays.stream(entity.get_5aamal().split(","))
                        .map(s -> s.isBlank() ? -1 : Integer.parseInt(s))
                        .collect(Collectors.toList()))
                .jamath(Arrays.stream(entity.getJamath().split(","))
                        .map(s -> s.isBlank() ? -1 : Integer.parseInt(s))
                        .collect(Collectors.toList()))
                .build();

    }

    // DTO -> Entity
    public static JamathMasterSurveyEntity toEntity(JamathSurveryDto dto) {
        if (dto == null) return null;

        return JamathMasterSurveyEntity.builder().id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .name(dto.getName()).email(dto.getEmail()).service(dto.getService())
                .gender(dto.getGender())
                .phone(dto.getPhone()).age(dto.getAge()).comment(dto.getComment()).agreeTerms(dto.isAgreeTerms())

                .masjidId(UUID.fromString(dto.getMasjidId()))

                ._5aamal(dto.get_5aamal().stream().map(String::valueOf).collect(Collectors.joining(",")))
                .jamath(dto.getJamath().stream().map(String::valueOf).collect(Collectors.joining(",")))
                .image(dto.getImage())
                //.image(dto.getImage() != null && !dto.getImage().isEmpty() ? dto.getImage() : "/profiles/avt_man.jpg")
                .build();
    }

    // List<Entity> -> List<DTO>
    public static List<JamathSurveryDto> toDTOList(List<JamathMasterSurveyEntity> entities) {
        return entities.stream().map(JamathSurveyDtoConverterToEntity::toDTO).collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<JamathMasterSurveyEntity> toEntityList(List<JamathSurveryDto> dtos) {
        return dtos.stream().map(JamathSurveyDtoConverterToEntity::toEntity).collect(Collectors.toList());
    }

    public static void updateEntity(JamathMasterSurveyEntity entity, JamathSurveryDto dto) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setService(dto.getService());

        entity.setPhone(dto.getPhone());
        entity.setAge(dto.getAge());
        //entity.setComment(dto.getComment());
        entity.setAgreeTerms(dto.isAgreeTerms());
        entity.setGender(dto.getGender());

        entity.setMasjidId(UUID.fromString(dto.getMasjidId()));

        entity.set_5aamal(dto.get_5aamal().stream().map(String::valueOf).collect(Collectors.joining(",")));
        entity.setJamath(dto.getJamath().stream().map(String::valueOf).collect(Collectors.joining(",")));
        //entity.setImage(dto.getImage() != null && !dto.getImage().isEmpty() ? dto.getImage() : "/profiles/avt_man.jpg");
        entity.setImage(dto.getImage());
    }
}
