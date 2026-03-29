package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.BroadCastMessageDto;
import com.tpty.tableegi.jamath.dto.JamathFeedBackDto;
import com.tpty.tableegi.jamath.entity.BroadCastMessageEntity;
import com.tpty.tableegi.jamath.entity.JamathiFeedBackEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class JamathiFeedBackDtoConverterToEntity {

    // Entity -> DTO
    public static JamathFeedBackDto toDTO(JamathiFeedBackEntity entity) {
        if (entity == null) return null;

        return JamathFeedBackDto.builder()
                .id(entity.getId() != null ? entity.getId().toString() : null)
                .name(entity.getName())
                .email(entity.getEmail())
                .rating(entity.getRating())
                .feedBack(entity.getFeedBack())
                .build();
    }

    // DTO -> Entity
    public static JamathiFeedBackEntity toEntity(JamathFeedBackDto dto) {
        if (dto == null) return null;

        return JamathiFeedBackEntity.builder()
                .id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .name(dto.getName())
                .email(dto.getEmail())
                .feedBack(dto.getFeedBack())
                .rating(dto.getRating())
                .build();
    }

    // List<Entity> -> List<DTO>
    public static List<JamathFeedBackDto> toDTOList(List<JamathiFeedBackEntity> entities) {
        return entities.stream()
                .map(JamathiFeedBackDtoConverterToEntity::toDTO)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<JamathiFeedBackEntity> toEntityList(List<JamathFeedBackDto> dtos) {
        return dtos.stream()
                .map(JamathiFeedBackDtoConverterToEntity::toEntity)
                .collect(Collectors.toList());
    }
}
