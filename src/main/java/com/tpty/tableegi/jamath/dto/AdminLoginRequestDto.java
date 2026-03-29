package com.tpty.tableegi.jamath.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminLoginRequestDto {

    private String id;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private UUID sessionid;
    private LocalDateTime lastUpdateDate;
    private LocalDateTime createDate;
    private boolean isAdminLogin;
    private String usrAdminId;
    @JsonProperty("isSuperAdmin")
    private boolean isSuperAdmin;
    private String locationId;
}
