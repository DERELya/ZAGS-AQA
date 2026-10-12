package api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AdministrationApplicantionDto {
    @JsonProperty("applId")
    private long applId;
    @JsonProperty("staffid")
    private long staffid;
    @JsonProperty("action")
    private String action;
}
