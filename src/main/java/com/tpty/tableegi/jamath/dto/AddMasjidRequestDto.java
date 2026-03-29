package com.tpty.tableegi.jamath.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddMasjidRequestDto {
    private String id;
    private String masjidName;
    private int halkaNo;
    private String address;

    private String adminUsrTrackingId;
    private String username;
    private String sessionid;
    private String usrAdminId;
    private String locationId;
}
