package com.mydiary.my_diary_server.controller;
import com.mydiary.my_diary_server.domain.Diary;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.DiaryResponse;
import com.mydiary.my_diary_server.dto.UpdateDiaryRequest;
import com.mydiary.my_diary_server.service.DiaryService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.models.annotations.OpenAPI31;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class DiaryApiController {

    private final DiaryService diaryService;


    @Operation (summary = "일기 등록")
    @PostMapping("/api/diaries")
    public ResponseEntity<Diary> addDiary( @RequestBody AddDiaryRequest request, Principal principal) {
        Diary savedDiary = diaryService.save(request, principal.getName());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }

    @Operation (summary = "모든 일기 검색")
    @GetMapping("/api/diaries")
    public ResponseEntity<List<DiaryResponse>> findAllDiaries() {
        List<DiaryResponse> diaries = diaryService.findAll()
                .stream()
                .map(DiaryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }

    @Operation (summary = "나의 모든 일기 검색")
    @GetMapping("/api/my/diaries/")
    public ResponseEntity<List<DiaryResponse>> findAllMyDiaries(Principal principal) {
        List<DiaryResponse> diaries = diaryService.findByEmail(principal.getName())
                .stream()
                .map(DiaryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }

    @Operation (summary = "diaryId 값을 파라미터에 입력하면 해당 일기 검색")
    @GetMapping("/api/diaries/{id}")
    public ResponseEntity<DiaryResponse> findDiary(@PathVariable long id) {
        Diary diary = diaryService.findById(id);

        return ResponseEntity.ok()
                .body(new DiaryResponse(diary));
    }
    @Operation (summary = "diaryId 값을 파라미터에 입력하면 해당 일기 삭제")
    @DeleteMapping("/api/diaries/{id}")
    public ResponseEntity<Void> deleteDiary(@PathVariable long id) {
        diaryService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @Operation (summary = "diaryId 값을 파라미터에 입력하면 해당 일기 업데이트")
    @PutMapping("/api/diaries/{id}")
    public ResponseEntity<Diary> updateDiaries(@PathVariable long id,
                                               @RequestBody UpdateDiaryRequest request) {
        Diary updatedDiary = diaryService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedDiary);
    }

}
