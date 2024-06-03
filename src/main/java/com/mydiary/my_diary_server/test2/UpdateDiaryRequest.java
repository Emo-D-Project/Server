package com.mydiary.my_diary_server.test2;

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
    
    public Diary toEntity() {
        return Diary.builder()
                .content(content)
                .emotion(emotion)
                .is_share(is_share)
                .is_comm(is_comm)
                .build();
    }
}
