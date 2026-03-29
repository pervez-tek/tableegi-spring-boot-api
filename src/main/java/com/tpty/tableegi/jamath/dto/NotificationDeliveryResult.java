package com.tpty.tableegi.jamath.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tpty.tableegi.jamath.utils.NotificationChannel;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationDeliveryResult {

    private NotificationChannel channel; // e.g. "EMAIL", "SMS", "WHATSAPP"
    private boolean success;
    private String source;
    private String errorMessage;
}
