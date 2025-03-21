package com.mydiary.my_diary_server.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CompletionRequestDto {

    private String model;
    private String prompt;
    private float temperature;

    @Builder
    CompletionRequestDto(String model, String prompt, float temperature){
        this.model = model;
        this.prompt = prompt;
        this.temperature = temperature;
    }
}
