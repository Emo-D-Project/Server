package com.mydiary.my_diary_server.test2;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RunRequestDTO (
        @JsonProperty("assistant_id")
        String assistantId) {}