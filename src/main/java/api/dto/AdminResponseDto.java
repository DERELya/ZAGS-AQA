package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AdminResponseDto {
    private AdminDataDto data;
    private String requestId;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AdminDataDto {

        @JsonProperty("staffid")
        private Long staffId;
    }
}
