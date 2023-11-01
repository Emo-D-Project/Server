package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddDiaryRequest {
    private String title;
    private String content;
    private LocalDate date;

    public Diary toEntity(String author) {
        return Diary.builder()
                .title(title)
                .content(content)
                .date(date)
                .author(author)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
