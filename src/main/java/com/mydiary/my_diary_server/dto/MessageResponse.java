package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Message;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageResponse {
    private final String content;
    private final Long senderId;
    private final Long receiverId;
    private boolean isMyMessage;
    private final LocalDateTime sentAt;

    public MessageResponse(Message message) {
        this.content = message.getContent();
        this.senderId = message.getSenderId();
        this.receiverId = message.getReceiverId();
        this.sentAt = message.getSentAt();
    }
}
