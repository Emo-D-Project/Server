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

    public ChatRoom(Long otherUserId, String s) {
        this.otherUserId = otherUserId;
        this.lastMessage = s;
    }


    public void setLastMessage(String content) {
        this.lastMessage = content;
    }

    public void setLastMessageSentAt(LocalDateTime sentAt) {
        this.lastMessageSentAt = sentAt;
    }

}
