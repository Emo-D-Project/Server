package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDiaryRequest {
    private long user_id;
    private String content;
    private String emotion;
    private Boolean is_share;
    private Boolean is_comm;
    
    public Diary toEntity(Diary diary) {
        return Diary.builder()
                .user_id(diary.getUser_id())
                .content(diary.getContent())
                .emotion(diary.getEmotion())
                .is_share(diary.getIs_share())
                .is_comm(diary.getIs_comm())
                .build();
    }
}
