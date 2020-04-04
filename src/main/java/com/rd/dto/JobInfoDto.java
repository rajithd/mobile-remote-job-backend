package com.rd.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobInfoDto {

    private String id;
    private String position;
    private String company;
    private String companyLogo;
    private String description;
    private String url;
}
