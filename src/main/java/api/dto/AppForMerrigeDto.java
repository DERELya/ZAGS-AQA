package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppForMerrigeDto {

    @JsonProperty("applicantid")
    private Integer applicantId;

    @JsonProperty("citizenid")
    private Integer citizenId;

    @JsonProperty("applicationid")
    private Integer applicationId;

    @JsonProperty("merrigecertificateid")
    private Integer merrigeCertificateId;
}
