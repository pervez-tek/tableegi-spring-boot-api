package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.AddMasjidRequestDto;
import com.tpty.tableegi.jamath.dto.LocationsMstrRequestDto;
import com.tpty.tableegi.jamath.entity.AddMasjidDataEntity;
import com.tpty.tableegi.jamath.entity.LocationDataEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LocationsMstrDtoConverterToEntity {

    // Entity -> DTO
    public static LocationsMstrRequestDto toDTO(LocationDataEntity entity) {
        if (entity == null) return null;

        return LocationsMstrRequestDto.builder()
                .id(entity.getId() != null ? entity.getId().toString() : null)
                .name(entity.getName())
                .halka(entity.getHalka())
                .state(entity.getState())
                .country(entity.getCountry())
                .lat(entity.getLat())
                .lon(entity.getLon())
                .shortName(entity.getShortName())
                .sequenceNo(entity.getSequenceNo())
                .build();
    }

    // DTO -> Entity
    public static LocationDataEntity toEntity(LocationsMstrRequestDto dto) {
        if (dto == null) return null;

        return LocationDataEntity.builder()
                .id(dto.getId() != null && !dto.getId().isEmpty() ? UUID.fromString(dto.getId()) : null)
//                .id(Optional.ofNullable(dto.getId())
//                        .filter(id -> !id.isEmpty())
//                        .map(UUID::fromString)
//                        .orElse(null))

                .name(dto.getName())
                .halka(dto.getHalka())
                .state(dto.getState())
                .country(dto.getCountry())
                .lat(dto.getLat())
                .lon(dto.getLon())
                .shortName(dto.getShortName())
                .sequenceNo(dto.getSequenceNo())
                .build();
    }

    // List<Entity> -> List<DTO>
    public static List<LocationsMstrRequestDto> toDTOList(List<LocationDataEntity> entities) {
        return entities.stream()
                .map(LocationsMstrDtoConverterToEntity::toDTO)
                .collect(Collectors.toList());
    }

    // List<DTO> -> List<Entity>
    public static List<LocationDataEntity> toEntityList(List<LocationsMstrRequestDto> dtos) {
        return dtos.stream()
                .map(LocationsMstrDtoConverterToEntity::toEntity)
                .collect(Collectors.toList());
    }
}
