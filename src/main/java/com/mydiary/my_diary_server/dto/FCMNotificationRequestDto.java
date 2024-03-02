package com.mydiary.my_diary_server.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class FCMNotificationRequestDto{
    private Long targetUserId;
    private String title;
    private String body;

    @Builder
    public FCMNotificationRequestDto(Long targetUserId, String title, String body){
        this.targetUserId = targetUserId;
        this.title = title;
        this.body = body;

    }
}
