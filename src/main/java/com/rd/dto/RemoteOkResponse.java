package com.rd.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RemoteOkResponse extends CommonResponse {

    private String slug;
    private Long epoch;
    private String company;

    @JsonProperty("company_logo")
    private String companyLogo;

    private String position;

    private String[] tags;

    private String description;

    private String url;

}
