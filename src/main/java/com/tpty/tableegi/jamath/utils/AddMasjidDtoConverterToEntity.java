package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class AddMasjidDtoConverterToEntity {

    // Entity -> DTO
    public static AddMasjidRequestDto toDTO(AddMasjidDataEntity entity) {
        if (entity == null) return null;

        return AddMasjidRequestDto.builder()
                .id(entity.getId() != null ? entity.getId().toString() : null)
                .halkaNo(entity.getHalkaNo())
                .masjidName(entity.getName())
                .address(entity.getAddress())
                .usrAdminId(entity.getUsrAdminId() != null ? entity.getUsrAdminId().toString() : null)
                .locationId(entity.getLocationId() != null ? entity.getLocationId().toString() : null)
                .build();
    }

    // DTO -> Entity
    public static AddMasjidDataEntity toEntity(AddMasjidRequestDto dto) {
        if (dto == null) return null;

        return AddMasjidDataEntity.builder()
                .id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .name(dto.getMasjidName())
                .halkaNo(dto.getHalkaNo())
                .address(dto.getAddress())
                .usrAdminId(dto.getUsrAdminId() != null && !dto.getUsrAdminId().isEmpty() ? UUID.fromString(dto.getUsrAdminId()) : null)
                .locationId(dto.getLocationId() != null && !dto.getLocationId().isEmpty() ? UUID.fromString(dto.getLocationId()) : null)

                .build();
    }

    // List<Entity> -> List<DTO>
    public static List<AddMasjidRequestDto> toDTOList(List<AddMasjidDataEntity> entities) {
        return entities.stream()
                .map(AddMasjidDtoConverterToEntity::toDTO)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<AddMasjidDataEntity> toEntityList(List<AddMasjidRequestDto> dtos) {
        return dtos.stream()
                .map(AddMasjidDtoConverterToEntity::toEntity)
                .collect(Collectors.toList());
    }
}
