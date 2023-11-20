package com.mydiary.my_diary_server.controller;
import com.mydiary.my_diary_server.domain.*;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.*;
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
@RequestMapping("/api/diaries")
public class DiaryApiController {

    private final DiaryService diaryService;

   
    @PostMapping("/create")
   @Operation(summary="일기 등록")
    public ResponseEntity<Diary> addDiary
    (@RequestBody AddDiaryRequest request, Principal principal) {
    	Diary savedDiary = diaryService.save(request, principal.getName());
    	
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }

    @GetMapping("/read")
    @Operation(summary="일기 전체 읽기")
    public ResponseEntity<List<DiaryResponse>> findAlldiaries() {
        List<DiaryResponse> diaries = diaryService.findAll()
                .stream()
                .map(DiaryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }
    
    @GetMapping("/read/{id}")
    @Operation(summary="특정 일기 읽기")
    public ResponseEntity<DiaryResponse> findDiary(@PathVariable long id) {
        Diary diary = diaryService.findById(id);

        return ResponseEntity.ok()
                .body(new DiaryResponse(diary));
    }

    @GetMapping("/mine/{userid}")
    @Operation(summary="자신의 일기 불러오기")
    public ResponseEntity<List<DiaryResponse>> findmydiaries(Principal principal)
    {
    	List<DiaryResponse> diaries = diaryService.findMine(Long.parseLong(principal.getName()))
                .stream()
                .map(DiaryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }
    // 주석추가
    
    @GetMapping("/report")
    @Operation(summary="감정통계")
    public DiaryResponse analysis()
    {
    	return null;
    }
    
    @DeleteMapping("/delete/{id}")
    @Operation(summary="일기 삭제")
    public ResponseEntity<Void> deleteDiary(@PathVariable long id) {
        diaryService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/change/{id}")
    @Operation(summary="일기 수정")
    public ResponseEntity<Diary> updateDiaries(@PathVariable long id,
                                               @RequestBody UpdateDiaryRequest request) {
        Diary updatedDiary = diaryService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedDiary);
    }
    
    @PutMapping("/recommend/{id}")
    @Operation(summary="공감")
    public void recommendDiary(@PathVariable long id, Principal principal)
    {
    	LikesDTO like = new LikesDTO(id, Long.parseLong(principal.getName()));
    	diaryService.recommend(like);
    }

    // 주석추가
    // 하나 더 추가
    // 또11111111144444222222222333366661111122223333344444555555555666777777
  
}


