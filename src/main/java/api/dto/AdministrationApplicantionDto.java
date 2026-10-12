package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdministrationApplicantionDto {
    @JsonProperty("applId")
    private Long applId;
    @JsonProperty("staffid")
    private Long staffid;
    @JsonProperty("action")
    private String action;
}
