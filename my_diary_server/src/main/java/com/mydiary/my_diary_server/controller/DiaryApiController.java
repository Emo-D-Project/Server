package com.mydiary.my_diary_server.controller;
import com.mydiary.my_diary_server.domain.Diary;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.DiaryResponse;
import com.mydiary.my_diary_server.dto.UpdateDiaryRequest;
import com.mydiary.my_diary_server.service.DiaryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryApiController {

    private final DiaryService diaryService;

    
    @PostMapping("/api/diaries")
    public ResponseEntity<Diary> addDiary
    (@RequestBody AddDiaryRequest request) {
        Diary savedDiary = diaryService.save
        		(new Diary(request.getUser_id(), request.getEmotion(), request.getContent(), request.getIs_share(), request.getIs_comm()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }

    @GetMapping("/api/diaries")
    public ResponseEntity<List<DiaryResponse>> findAlldiaries() {
        List<DiaryResponse> diaries = diaryService.findAll()
                .stream()
                .map(DiaryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }
    
    @GetMapping("/api/diaries/{id}")
    public ResponseEntity<DiaryResponse> findDiary(@PathVariable long id) {
        Diary diary = diaryService.findById(id);

        return ResponseEntity.ok()
                .body(new DiaryResponse(diary));
    }

    @DeleteMapping("/api/diaries/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable long id) {
        diaryService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/diaries/{id}")
    public ResponseEntity<Diary> updateDiaries(@PathVariable long id,
                                               @RequestBody UpdateDiaryRequest request) {
        Diary updatedDiary = diaryService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedDiary);
    }
}


