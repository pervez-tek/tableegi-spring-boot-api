package com.tpty.tableegi.jamath.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BroadCastMessageDto {

    private String id;
    private String subject;
    @Size(max = 300, message = "Message cannot exceed 300 characters")
    private String message;
    private String usrAdminId;
}
