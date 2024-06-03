package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.domain.Diary;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.DiaryResponse;
import com.mydiary.my_diary_server.dto.LikesDTO;
import com.mydiary.my_diary_server.dto.UpdateDiaryRequest;
import com.mydiary.my_diary_server.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/diaries")
public class DiaryApiController {

    private final DiaryService diaryService;

       
    @PostMapping(value = "create", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary="일기 등록")
    public ResponseEntity<com.mydiary.my_diary_server.domain.Diary> addDiary
            (@RequestPart AddDiaryRequest request, @RequestPart(required=false) List<MultipartFile> imageFile,
    		@RequestPart(required=false) MultipartFile audioFile,
    		Principal principal) throws Exception {
    
        com.mydiary.my_diary_server.domain.Diary savedDiary = diaryService.save(request, imageFile, audioFile, principal.getName());
    	
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }
    
    @PostMapping(value = "createTest", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary="내부 api")
    public ResponseEntity<com.mydiary.my_diary_server.domain.Diary> addDiaryTest
            (@RequestBody AddDiaryRequest request, @RequestParam int day, Principal principal) throws Exception {
    
        com.mydiary.my_diary_server.domain.Diary savedDiary = diaryService.saveTest(request, day, null, null, principal.getName());
    	
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedDiary);
    }
    
    
    @GetMapping("/read")
    @Operation(summary="일기 전체 읽기")
    public ResponseEntity<List<com.mydiary.my_diary_server.dto.DiaryResponse>> findAlldiaries() throws Exception {
        List<com.mydiary.my_diary_server.dto.DiaryResponse> diaries = diaryService.findAll()
                .stream()
                .map(com.mydiary.my_diary_server.dto.DiaryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }
    
    

    
    @GetMapping("/read/{id}")
    @Operation(summary="특정 일기 읽기")
    public ResponseEntity<com.mydiary.my_diary_server.dto.DiaryResponse> findDiary(@PathVariable long id) throws Exception {
        com.mydiary.my_diary_server.domain.Diary diary = diaryService.findById(id);

        return ResponseEntity.ok()
                .body(new com.mydiary.my_diary_server.dto.DiaryResponse(diary));
    }

    @GetMapping("/mine/{userid}")
    @Operation(summary="자신의 일기 불러오기")
    public ResponseEntity<List<com.mydiary.my_diary_server.dto.DiaryResponse>> findmydiaries(Principal principal) throws Exception {
    	List<com.mydiary.my_diary_server.dto.DiaryResponse> diaries = diaryService.findMine(Long.parseLong(principal.getName()))
                .stream()
                .map(com.mydiary.my_diary_server.dto.DiaryResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(diaries);
    }

    @GetMapping("/readTest")
    @Operation(summary="내부테스트용")
    public List<String> findWeek(Principal principal) throws Exception{
    	List<String> result = new ArrayList<String>();
    	
    	List<com.mydiary.my_diary_server.dto.DiaryResponse> diaries = diaryService.findWeek(Long.parseLong(principal.getName()))
                .stream()
                .map(com.mydiary.my_diary_server.dto.DiaryResponse::new)
                .toList();

    	for(com.mydiary.my_diary_server.dto.DiaryResponse res : diaries)
    	{
    		result.add(res.getContent());
    	}
    	
        return result;
    }
    
    
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
    public ResponseEntity<com.mydiary.my_diary_server.domain.Diary> updateDiaries(@PathVariable long id,
                                                                                  @RequestBody UpdateDiaryRequest request) throws Exception {
        Diary updatedDiary = diaryService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedDiary);
    }
    
    @PutMapping("/recommend/{id}")
    @Operation(summary="공감")
    public void recommendDiary(@PathVariable long id, Principal principal)
    {
    	com.mydiary.my_diary_server.dto.LikesDTO like = new LikesDTO(id, Long.parseLong(principal.getName()));
    	diaryService.recommend(like);
    }
    
    @GetMapping("/recommend/{id}")
    @Operation(summary="공감확인")
    public Integer recommendCheck(@PathVariable long id, Principal principal)
    {
    	return diaryService.checkLike(principal.getName(), id);
    }

  
}