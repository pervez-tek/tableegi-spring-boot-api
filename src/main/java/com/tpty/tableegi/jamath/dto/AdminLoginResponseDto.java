package com.tpty.tableegi.jamath.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminLoginResponseDto {

    private String id;
    private String username;
    private UUID sessionid;
}


