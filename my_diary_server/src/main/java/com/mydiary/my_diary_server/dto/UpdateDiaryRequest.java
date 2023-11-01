package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateDiaryRequest {
    private String emotion;
    private String content;
    private Boolean is_share;
    private Boolean is_comm;
    
    public Diary toEntity(Diary diary) {
        return Diary.builder()
                .content(diary.getContent())
                .emotion(diary.getEmotion())
                .is_share(diary.getIs_share())
                .is_comm(diary.getIs_comm())
                .build();
    }
}
