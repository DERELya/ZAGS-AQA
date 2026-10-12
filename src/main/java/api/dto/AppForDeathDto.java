package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppForDeathDto {

    @JsonProperty("applicantid")
    private Long applicantId;

    @JsonProperty("citizenid")
    private Long citizenId;

    @JsonProperty("applicationid")
    private Long applicationId;

    @JsonProperty("deathcertificateid")
    private Long deathCertificateId;
}