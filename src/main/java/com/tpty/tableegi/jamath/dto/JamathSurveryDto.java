package com.tpty.tableegi.jamath.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JamathSurveryDto {

    private String id;

    private String name;

    private String email;

    private String service;

    private String phone;

    private int age;

    private char gender;

    private List<Integer> jamath;

    private List<Integer> _5aamal;

    private String comment;

    private String image;

    private boolean agreeTerms;

    private String masjidId;

    private String namaziId;

    private String comments;

}
