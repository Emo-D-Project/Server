package com.mydiary.my_diary_server.controller;
import com.mydiary.my_diary_server.data.dto.DiaryDTO;
import com.mydiary.my_diary_server.data.dto.DiaryResponseDTO;
import com.mydiary.my_diary_server.service.DiaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/diaries")
public class DiaryController {

    private DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService){this.diaryService = diaryService;}

    @ApiOperation(value = "일기 생성과 저장을 하는 기능")
    @PostMapping
    public <content> ResponseEntity<DiaryResponseDTO> createDiary(
            @RequestParam String title,
            @RequestParam String content) {
        DiaryDTO diaryDTO = new DiaryDTO(title, content, LocalDate.now());
        DiaryResponseDTO createdDiary = diaryService.saveDiary(diaryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdDiary);
    }

    @ApiOperation(value = "다이어리의 정보를 불러오는 기능")
    @GetMapping()
    public ResponseEntity<DiaryResponseDTO> getDiary(@ApiParam(value = "불러올 상품 번호")@RequestParam Long number) {
        DiaryResponseDTO diary = diaryService.getDiary(number);
        if (diary != null) {
            return ResponseEntity.ok(diary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "일기를 수정하는 기능")
    @PutMapping
    public ResponseEntity<DiaryResponseDTO> changeDiary(@ApiParam (value = "수정하려는 상품 정보 ") @RequestParam Long number, DiaryDTO diaryDTO) throws ChangeSetPersister.NotFoundException {
        DiaryResponseDTO diaryResponseDTO = diaryService.changeDiary(number, diaryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDTO);
    }

    @ApiOperation(value = "일기를 삭제하는능")
    @DeleteMapping
    public ResponseEntity<String> deleteDiary(@ApiParam(value = "삭제하려는 상품 번호") @RequestParam Long number) throws Exception {
        diaryService.deleteDiary(number);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
