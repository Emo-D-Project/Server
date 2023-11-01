package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Diary;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.UpdateDiaryRequest;
import com.mydiary.my_diary_server.repository.DiaryRepository;
import org.springframework.stereotype.Service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public Diary save(AddDiaryRequest request, String userName) {
        return diaryRepository.save(request.toEntity(userName));
    }

    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }


    public List<Diary> findByEmail(String author) {
        return diaryRepository.findByAuthor(author)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + author));

    }

    public Diary findById(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public void delete(long id) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeDiaryWriter(diary);
        diaryRepository.delete(diary);
    }

    @Transactional
    public Diary update(long id, UpdateDiaryRequest request) {
        Diary diary = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeDiaryWriter(diary);
        diary.update(request.getTitle(), request.getContent());

        return diary;
    }

    // 일기를 작성한 유저인지 확인
    private static void authorizeDiaryWriter(Diary diary) {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!diary.getAuthor().equals(author)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

}