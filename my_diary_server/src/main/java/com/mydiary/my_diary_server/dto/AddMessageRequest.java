package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddMessageRequest {
    private String content;
    private LocalDateTime sentAt;
    private Long receiverId;
    private boolean isRead;

}
