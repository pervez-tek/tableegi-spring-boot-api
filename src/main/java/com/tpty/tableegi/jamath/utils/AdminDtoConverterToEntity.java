package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.dto.JamathSurveryDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;
import com.tpty.tableegi.jamath.entity.JamathMasterSurveyEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdminDtoConverterToEntity {

    // Entity -> DTO
    public static AdminLoginRequestDto toDTO(AdminLoginEntity entity) {
        if (entity == null) return null;

        return AdminLoginRequestDto.builder().id(entity.getId() != null ? entity.getId().toString() : null).username(entity.getUsername()).password(entity.getPassword()).createDate(entity.getCreateDate()).lastUpdateDate(entity.getLastUpdateDate()).isSuperAdmin(entity.isSuperAdmin()).locationId(entity.getLocationId() != null ? entity.getLocationId().toString() : null).build();
    }

    // DTO -> Entity
    public static AdminLoginEntity toEntity(AdminLoginRequestDto dto) {
        if (dto == null) return null;

        return AdminLoginEntity.builder().id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .username(dto.getUsername()).password(dto.getPassword()).createDate(dto.getCreateDate()).lastUpdateDate(dto.getLastUpdateDate()).isSuperAdmin(dto.isSuperAdmin()).locationId(dto.getLocationId() != null && !dto.getLocationId().isEmpty() ? UUID.fromString(dto.getLocationId()) : null).build();
    }

    // List<Entity> -> List<DTO>
    public static List<AdminLoginRequestDto> toDTOList(List<AdminLoginEntity> entities) {
        return entities.stream().map(AdminDtoConverterToEntity::toDTO).collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<AdminLoginEntity> toEntityList(List<AdminLoginRequestDto> dtos) {
        return dtos.stream().map(AdminDtoConverterToEntity::toEntity).collect(Collectors.toList());
    }

    public static void updateEntity(AdminLoginEntity entity, AdminLoginRequestDto dto) {
        entity.setUsername(dto.getUsername());
        entity.setId(UUID.fromString(dto.getId()));
        entity.setLastUpdateDate(LocalDateTime.now());

    }
}
