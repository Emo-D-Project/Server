package com.mydiary.my_diary_server.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    private Long otherUserId;
    private String name;
    private String lastMessage;
    private LocalDateTime lastMessageSentAt;
    private boolean isRead;

    public ChatRoom(Long otherUserId, String s, boolean isRead) {
        this.otherUserId = otherUserId;
        this.lastMessage = s;
        this.isRead = isRead;
    }


    public void setLastMessage(String content) {
        this.lastMessage = content;
    }

    public void setLastMessageSentAt(LocalDateTime sentAt) {
        this.lastMessageSentAt = sentAt;
    }

}
