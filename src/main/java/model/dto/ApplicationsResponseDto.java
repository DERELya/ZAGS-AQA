package model.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationsResponseDto {
    private String total;
    private List<ApplicationDto> data;
    private String requestId;
}
