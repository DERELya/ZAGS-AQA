package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminRequestDto {

    private String personalLastName;
    private String personalFirstName;
    private String personalMiddleName;
    private String personalPhoneNumber;
    private String personalNumberOfPassport;

    @JsonProperty("dateofbirth")
    private String dateOfBirth;
}