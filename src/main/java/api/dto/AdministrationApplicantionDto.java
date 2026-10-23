package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdministrationApplicantionDto {
    @JsonProperty("applId")
    private Integer applId;
    @JsonProperty("staffid")
    private Integer staffid;
    @JsonProperty("action")
    private String action;
}
