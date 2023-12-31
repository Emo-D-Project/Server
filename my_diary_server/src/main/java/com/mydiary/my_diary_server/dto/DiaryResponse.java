package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Diary;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
public class DiaryResponse {
    private final String emotion;
    private final String content;
    private long user_id;
    private long id;
    private int empathy;
    private LocalDateTime createdAt;
    private String audio;
    List<String> images = new ArrayList<>();
    private boolean is_comm;
    private boolean is_share;
    
    public DiaryResponse(Diary diary) {
        this.emotion = diary.getEmotion();
        this.content = diary.getContent();
        this.user_id = diary.getUserId();
        this.id = diary.getId();
        this.empathy = diary.getEmpathy();
        this.createdAt = diary.getCreatedAt();
        this.audio = diary.getAudio();
        this.images.add(diary.getImage1());
        this.images.add(diary.getImage2());
        this.images.add(diary.getImage3());
        this.is_comm = diary.getIs_comm();
        this.is_share = diary.getIs_share();
    }
}