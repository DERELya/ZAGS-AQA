package model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ApplicationDto {
    @JsonProperty("applicationid")
    private Integer applicationId;

    @JsonProperty("citizenid")
    private Integer citizenId;

    @JsonProperty("applicantid")
    private Integer applicantId;

    @JsonProperty("staffid")
    private Integer staffId;

    @JsonProperty("dateofapplication")
    private String dateOfApplication;

    @JsonProperty("kindofapplication")
    private String kindOfApplication;

    @JsonProperty("statusofapplication")
    private String statusOfApplication;

    private String channel;
    private String image;
}
