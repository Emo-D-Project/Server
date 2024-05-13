package com.mydiary.my_diary_server.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RunRequestDTO (
        @JsonProperty("assistant_id")
        String assistantId) {}