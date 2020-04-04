package com.rd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class GithubResponse extends CommonResponse {

    private String type;
    private String url;

    private String company;
    private String companyUrl;
    private String location;
    private String title;
    private String description;

    @JsonProperty("company_logo")
    private String companyIconUrl;

    @JsonProperty("how_to_apply")
    private String applyUrl;

    @JsonFormat(pattern = "EEE MMM dd HH:mm:ss zzz yyyy")
    private Date createdAt;
}
