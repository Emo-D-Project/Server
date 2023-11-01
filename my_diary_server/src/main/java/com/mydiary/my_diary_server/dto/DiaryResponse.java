package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.*;

import java.time.LocalDate;


@Getter
public class DiaryResponse {
    private final String title;
    private final String content;
    private final LocalDate date;
    private final Long diaryId;

    public DiaryResponse(Diary diary) {
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.date = diary.getDate();
        this.diaryId = diary.getId();
    }
}
