package com.mydiary.my_diary_server.dto;

import java.time.LocalDateTime;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDiaryRequest {
    private String content;
    private String emotion;
    private Boolean is_share;
    private Boolean is_comm;
    
    public Diary toEntity(String author) {
        return Diary.builder()
                .content(content)
                .emotion(emotion)
                .is_share(is_share)
                .is_comm(is_comm)
                .author(author)
                .CreatedAt(LocalDateTime.now())
                .build();
    }
}
