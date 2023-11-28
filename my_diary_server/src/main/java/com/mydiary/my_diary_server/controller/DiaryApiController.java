package com.mydiary.my_diary_server.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mydiary.my_diary_server.domain.*;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.*;
import com.mydiary.my_diary_server.dto.UpdateDiaryRequest;
import com.mydiary.my_diary_server.service.DiaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diaries")
public class DiaryApiController {

    private final DiaryService diaryService;
    
    /*
    @GetMapping("filecheck")
    public ResponseEntity<String> view()
    {
    		Files file = diaryService.view();
    		
            return file.getId()
    }
    */
 
    /*
    @PostMapping(value="upload", consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void upload(@RequestPart MultipartFile imageFile) throws IOException
    {
    	String imageByte = new String(imageFile.getBytes());
    	diaryService.upload(imageByte);
    }
    */
    
    @PostMapping("create")
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


  
}

