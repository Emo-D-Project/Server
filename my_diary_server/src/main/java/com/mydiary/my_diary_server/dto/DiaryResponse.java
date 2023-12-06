package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@AllArgsConstructor
public class DiaryResponse {
    private final String emotion;
    private final String content;
    private final long userId;
    private final long id;
    private final int empathy;
    private final LocalDateTime createdAt;
    private final String audio;
    private final List<String> images;

    public DiaryResponse(Diary diary) {
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.userId = diary.getUserId();
        this.id = diary.getId();
        this.empathy = diary.getEmpathy();
        this.createdAt = diary.getCreatedAt();
        this.audio = diary.getAudio();
        this.images = new ArrayList<>(diary.getImages());
    }
}
