package com.mydiary.my_diary_server.controller;
import com.mydiary.my_diary_server.dto.DiaryDTO;
import com.mydiary.my_diary_server.dto.DiaryResponseDTO;
import com.mydiary.my_diary_server.service.DiaryService;
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

    @PostMapping
    public <content> ResponseEntity<DiaryResponseDTO> addDiary(
            @RequestParam String title,
            @RequestParam String content) {
        DiaryDTO diaryDTO = new DiaryDTO(title, content, LocalDate.now());
        DiaryResponseDTO createdDiary = diaryService.saveDiary(diaryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(createdDiary);
    }

    @GetMapping()
    public ResponseEntity<DiaryResponseDTO> getDiary(@RequestParam Long number) {
        DiaryResponseDTO diary = diaryService.getDiary(number);
        if (diary != null) {
            return ResponseEntity.ok(diary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<DiaryResponseDTO> changeDiary( @RequestParam Long number, DiaryDTO diaryDTO) throws ChangeSetPersister.NotFoundException {
        DiaryResponseDTO diaryResponseDTO = diaryService.changeDiary(number, diaryDTO);
        return ResponseEntity.status(HttpStatus.OK).body(diaryResponseDTO);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteDiary( @RequestParam Long number) throws Exception {
        diaryService.deleteDiary(number);

        return ResponseEntity.status(HttpStatus.OK).body("정상적으로 삭제되었습니다.");
    }
}
