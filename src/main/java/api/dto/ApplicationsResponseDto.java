package api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationsResponseDto {
    private String total;
    private List<ApplicationDto> data;
    private String requestId;
}
