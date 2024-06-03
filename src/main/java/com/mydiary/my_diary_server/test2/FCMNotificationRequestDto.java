package com.mydiary.my_diary_server.test2;

import lombok.Builder;
import lombok.Data;

@Data
public class FCMNotificationRequestDto{
    private Long targetUserId;
    private String title;
    private String body;
    private Long postId;

    @Builder
    public FCMNotificationRequestDto(Long targetUserId, String title, String body, Long postId){
        this.targetUserId = targetUserId;
        this.title = title;
        this.body = body;
        this.postId = postId;
    }
}
