package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDiaryRequest {
    private String title;
    private String content;

    public Diary toEntity(String author) {
        return Diary.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
