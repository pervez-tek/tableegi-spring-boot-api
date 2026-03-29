package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.dto.BroadCastMessageDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.BroadCastMessageEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BroadCastMsgDtoConverterToEntity {

    // Entity -> DTO
    public static BroadCastMessageDto toDTO(BroadCastMessageEntity entity) {
        if (entity == null) return null;

        return BroadCastMessageDto.builder()
                .id(entity.getId() != null ? entity.getId().toString() : null)
                .subject(entity.getSubject())
                .message(entity.getMessage())
                .usrAdminId(entity.getUsrAdminId() != null ? entity.getUsrAdminId().toString() : null)
                .build();
    }

    // DTO -> Entity
    public static BroadCastMessageEntity toEntity(BroadCastMessageDto dto) {
        if (dto == null) return null;

        return BroadCastMessageEntity.builder()
                .id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .subject(dto.getSubject())
                .message(dto.getMessage())
                .usrAdminId(dto.getUsrAdminId() != null && !dto.getUsrAdminId().isEmpty() ? UUID.fromString(dto.getUsrAdminId()) : null)

                .build();
    }

    // List<Entity> -> List<DTO>
    public static List<BroadCastMessageDto> toDTOList(List<BroadCastMessageEntity> entities) {
        return entities.stream()
                .map(BroadCastMsgDtoConverterToEntity::toDTO)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<BroadCastMessageEntity> toEntityList(List<BroadCastMessageDto> dtos) {
        return dtos.stream()
                .map(BroadCastMsgDtoConverterToEntity::toEntity)
                .collect(Collectors.toList());
    }
}
