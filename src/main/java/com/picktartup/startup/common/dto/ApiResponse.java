package com.picktartup.startup.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private final int status;
    private final String message;
    private final String errorCode; // 추가된 부분
    private final T data;
}
