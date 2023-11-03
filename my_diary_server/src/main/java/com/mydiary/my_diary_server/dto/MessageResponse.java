package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class MessageResponse {
    private final String content;
    private final Long senderId;
    private final Long receiverId;
    private final LocalDateTime sentAt;

    public MessageResponse(Message message) {
        this.content = message.getContent();
        this.senderId = message.getSender().getId();
        this.receiverId = message.getReceiver().getId();
        this.sentAt = message.getSentAt();
    }
}
