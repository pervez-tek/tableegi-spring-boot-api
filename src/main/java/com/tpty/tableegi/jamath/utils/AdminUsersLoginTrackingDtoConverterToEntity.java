package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AdminUsersLoginTrackingEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AdminUsersLoginTrackingDtoConverterToEntity {

    // Entity -> DTO
    public static AdminLoginRequestDto toDTO(AdminUsersLoginTrackingEntity entity) {
        if (entity == null) return null;

        return AdminLoginRequestDto.builder()
                .id(entity.getId() != null ? entity.getId().toString() : null)
                .sessionid(entity.getSessionId())
                .isAdminLogin(entity.isAdminLogin())
                .isSuperAdmin(entity.isSuperAdmin())
                .locationId(entity.getLocationId())
                .usrAdminId(entity.getUsrAdminId() != null ? entity.getUsrAdminId().toString() : null)
                .build();
    }

    // DTO -> Entity
    public static AdminUsersLoginTrackingEntity toEntity(AdminLoginRequestDto dto) {
        if (dto == null) return null;

        return AdminUsersLoginTrackingEntity.builder()
                .id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .sessionId(dto.getSessionid())
                .isAdminLogin(dto.isAdminLogin())
                .isSuperAdmin(dto.isSuperAdmin())
                .locationId(dto.getLocationId())
                .usrAdminId(dto.getUsrAdminId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
                .build();
    }

    // List<Entity> -> List<DTO>
    public static List<AdminLoginRequestDto> toDTOList(List<AdminUsersLoginTrackingEntity> entities) {
        return entities.stream()
                .map(AdminUsersLoginTrackingDtoConverterToEntity::toDTO)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<AdminUsersLoginTrackingEntity> toEntityList(List<AdminLoginRequestDto> dtos) {
        return dtos.stream()
                .map(AdminUsersLoginTrackingDtoConverterToEntity::toEntity)
                .collect(Collectors.toList());
    }

    public static void updateEntity(AdminUsersLoginTrackingEntity entity, AdminLoginRequestDto dto) {


    }
}
