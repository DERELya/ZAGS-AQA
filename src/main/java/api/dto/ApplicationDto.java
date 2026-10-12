package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationDto {
    @JsonProperty("applicationid")
    private Long applicationId;

    @JsonProperty("citizenid")
    private Long citizenId;

    @JsonProperty("applicantid")
    private Long applicantId;

    @JsonProperty("staffid")
    private Long staffId;

    @JsonProperty("dateofapplication")
    private String dateOfApplication;

    @JsonProperty("kindofapplication")
    private String kindOfApplication;

    @JsonProperty("statusofapplication")
    private String statusOfApplication;

    private String channel;
    private String image;
}
