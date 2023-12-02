package com.mydiary.my_diary_server.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.mydiary.my_diary_server.domain.*;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.*;
import com.mydiary.my_diary_server.dto.UpdateDiaryRequest;
import com.mydiary.my_diary_server.service.DiaryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diaries")
public class DiaryApiController {

    private final DiaryService diaryService;
    private final Storage storage;
    @Value("${spring.cloud.gcp.storage.bucket}") // application.yml에 써둔 bucket 이름
    private String bucketName;

    @PostMapping(value = "create")
    @Operation(summary="일기 등록")
    public ResponseEntity<Diary> addDiary(
            @RequestBody AddDiaryRequest request,
            @RequestPart(required = false) List<MultipartFile> imageFiles,
            @RequestPart(required = false) MultipartFile audioFile,
            Principal principal) throws IOException {

        Diary diary = new Diary(Long.parseLong(principal.getName()), request.getContent(), request.getEmotion(), request.getIs_share(), request.getIs_comm());

        String url = "https://storage.googleapis.com/emod_project/";

        // 이미지 파일 처리
        List<String> imageUrls = new ArrayList<>();

        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile imageFile : imageFiles) {
                // 이미지 파일 형식 확인 (예: image/jpeg)
                String imageContentType = imageFile.getContentType();
                if (imageContentType != null && imageContentType.startsWith("image/")) {
                    // 이미지 파일을 처리하는 로직
                    String imageUrl = handleImageFile(imageFile);
                    imageUrls.add(imageUrl);
                } else {
                    // 이미지 파일 형식이 아닌 경우 처리 (예외 발생 또는 무시)
                    System.out.println("Invalid image file format: " + imageContentType);
                }
            }
        }
        // 이미지 파일이 존재한다면 이미지 저장
        if(!imageUrls.isEmpty()){
            diary.setImages(imageUrls);
        }
        // 오디오 파일이 존재한다면 오디오 파일 저장
        if (audioFile != null && !audioFile.isEmpty()) {
            String audioUrl = handleAudioFile(audioFile);
            diary.setAudio(audioUrl);
        }


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(diary);
    }

    // 이미지 파일 처리 메소드
    private String handleImageFile(MultipartFile imageFile) throws IOException {
        String url = "https://storage.googleapis.com/emod_project/";
        // 클라우드에 이미지 업로드
        String uuid = UUID.randomUUID().toString();
        String ext = imageFile.getContentType();

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                imageFile.getInputStream()
        );

        log.info("blobinfo/bucekt:  " + blobInfo.getBucket());

        return url + uuid; // 실제 이미지 URL
    }
    // 오디오 파일 처리 로직
    private String handleAudioFile(MultipartFile audioFile) throws IOException {
        String url = "https://storage.googleapis.com/emod_project/";
        // 클라우드에 오디오 업로드
        String uuid = UUID.randomUUID().toString();
        String ext = audioFile.getContentType();

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                audioFile.getInputStream()
        );

        log.info("blobinfo/bucekt: " + blobInfo.getBucket());

        return url + uuid; // 실제 오디오 URL
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
    
    @GetMapping("/recommend/{id}")
    @Operation(summary="공감확인")
    public Integer recommendCheck(@PathVariable long id, Principal principal)
    {
    	return diaryService.checkLike(principal.getName(), id);
    }

  
}

