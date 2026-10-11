package model.dto;

import lombok.Data;

@Data
public class ApplicationResponseDto<T> {
    private T data;
    private String requestId;
}
