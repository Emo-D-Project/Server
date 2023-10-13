package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.data.dto.DiaryDTO;
import com.mydiary.my_diary_server.data.dto.DiaryResponseDTO;
import org.springframework.data.crossstore.ChangeSetPersister;

public interface DiaryService {

    DiaryResponseDTO getDiary(Long number);

    DiaryResponseDTO saveDiary(DiaryDTO diaryDTO);

    DiaryResponseDTO changeDiary(Long number, DiaryDTO diaryDTO) throws ChangeSetPersister.NotFoundException;

    void deleteDiary(Long number) throws Exception;
}
