package com.mydiary.my_diary_server.test2;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddDiaryRequest {
    private String content;
    private String emotion;
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
