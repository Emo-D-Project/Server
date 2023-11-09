package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.*;


@Getter
public class DiaryResponse {
    private final String emotion;
    private final String content;
    private long user_id;
    private long id;
    private int empathy;
    
    public DiaryResponse(Diary diary) {
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.user_id = diary.getUserId();
        this.id = diary.getId();
        this.empathy = diary.getEmpathy();
    }
}
