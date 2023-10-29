package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.*;


@Getter
public class DiaryResponse {
    private final String title;
    private final String content;

    public DiaryResponse(Diary article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
