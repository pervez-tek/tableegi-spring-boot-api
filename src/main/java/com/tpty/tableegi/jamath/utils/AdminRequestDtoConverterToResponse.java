package com.tpty.tableegi.jamath.utils;

import com.tpty.tableegi.jamath.dto.AdminLoginRequestDto;
import com.tpty.tableegi.jamath.dto.AdminLoginResponseDto;
import com.tpty.tableegi.jamath.entity.AdminLoginEntity;

import java.util.UUID;

public class AdminRequestDtoConverterToResponse {

    // Response Dto -> Request DTO
    public static AdminLoginRequestDto toResponseDto(AdminLoginResponseDto dresponseDto) {
        if (dresponseDto == null) return null;

        return AdminLoginRequestDto.builder()
                .id(dresponseDto.getId() != null ? dresponseDto.getId().toString() : null)
                .username(dresponseDto.getUsername())
                .sessionid(dresponseDto.getSessionid())
                .build();
    }

    // Request DTO -> Response Dto
    public static AdminLoginResponseDto toRequestDto(AdminLoginRequestDto dto) {
        if (dto == null) return null;

        return AdminLoginResponseDto.builder()
                .id(dto.getId() != null && !dto.getId().isEmpty() ? dto.getId() : null)

                .username(dto.getUsername())
                .sessionid(dto.getSessionid())
                .build();
    }
}
