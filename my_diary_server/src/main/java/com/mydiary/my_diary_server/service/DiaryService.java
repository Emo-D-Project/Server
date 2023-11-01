package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Diary;
import com.mydiary.my_diary_server.dto.AddDiaryRequest;
import com.mydiary.my_diary_server.dto.UpdateDiaryRequest;
import com.mydiary.my_diary_server.repository.DiaryRepository;
import org.springframework.stereotype.Service;



import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.List;
import com.mydiary.my_diary_server.service.UserService;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public Diary save(Diary diary) {
        return diaryRepository.save(diary);
    }

    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }
    
 

    public Diary findById(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }


    
    public void delete(long id) {
        Diary article = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

      //authorize
        diaryRepository.delete(article);
    }

    
    @Transactional
    public Diary update(long id, UpdateDiaryRequest request) {
        Diary article = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        //authorize
        article.update(request.getEmotion(), request.getContent(), request.getIs_share(), request.getIs_comm());

        return article;
    }

  
    // 일기를 작성한 유저인지 확인
    private static void authorizeArticleAuthor(long id, Principal principal) {
    	UserService serv = new UserService();
    	if (serv.findById(id).getUsername().equals(principal.getName()) ) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}