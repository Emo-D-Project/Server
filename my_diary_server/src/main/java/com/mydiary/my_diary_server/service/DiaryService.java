package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Diary;
import com.mydiary.my_diary_server.dto.DiaryDTO;
import com.mydiary.my_diary_server.dto.DiaryResponseDTO;
import com.mydiary.my_diary_server.repository.DiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    @Autowired
    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public DiaryResponseDTO getDiary(Long number) {
        Diary diary = diaryRepository.findById(number).get();

        DiaryResponseDTO diaryResponseDTO = new DiaryResponseDTO();
        diaryResponseDTO.setId(diary.getId());
        diaryResponseDTO.setTitle(diary.getTitle());
        diaryResponseDTO.setContent(diary.getContent());
        diaryResponseDTO.setDate(diary.getDate());

        return diaryResponseDTO;
    }

    public DiaryResponseDTO saveDiary(DiaryDTO diaryDTO) {
        Diary diary = new Diary();
        diary.setTitle(diaryDTO.getTitle());
        diary.setDate(diaryDTO.getDate());
        diary.setContent(diaryDTO.getContent());
        diary.setCreatedAt(LocalDateTime.now());
        diary.setUpdatedAt(LocalDateTime.now());

        Diary savedDiary = diaryRepository.save(diary);

        DiaryResponseDTO diaryResponseDTO = new DiaryResponseDTO();
        diaryResponseDTO.setTitle(savedDiary.getTitle());
        diaryResponseDTO.setContent(savedDiary.getContent());
        diaryResponseDTO.setId(savedDiary.getId());
        diaryResponseDTO.setDate(savedDiary.getDate());

        return diaryResponseDTO;
    }

    public DiaryResponseDTO changeDiary(Long number, DiaryDTO diaryDTO) throws ChangeSetPersister.NotFoundException {
        Optional<Diary> optionalDiary = diaryRepository.findById(number);

        Diary diary;

        if (optionalDiary.isPresent()) {
            diary = optionalDiary.get();

            Diary changedDiary = diaryRepository.save(diary);

            DiaryResponseDTO diaryResponseDTO = new DiaryResponseDTO();
            diaryResponseDTO.setId(changedDiary.getId());
            diaryResponseDTO.setTitle(changedDiary.getTitle());
            diaryResponseDTO.setContent(changedDiary.getContent());
            diaryResponseDTO.setDate(changedDiary.getDate());
            return diaryResponseDTO;

        } else {
            throw new ChangeSetPersister.NotFoundException();
        }



    }

    public void deleteDiary(Long number) throws Exception {
        diaryRepository.delete(diaryRepository.findById(number).get());

    }
}
