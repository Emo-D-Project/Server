package com.mydiary.my_diary_server.dto;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public record ThreadResponseDTO(
        String id,
        String object,
        @JsonProperty("created_at") long createdAt,
        Map<String, Object> metadata,
        ErrorInfo error) {

    // 에러 정보를 담기 위한 내부 클래스 정의
    public static record ErrorInfo(
            String message,
            String type,
            String param,
            String code
            ) {}
}