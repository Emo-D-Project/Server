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

    public Diary save(AddDiaryRequest req, String author) {
        return diaryRepository.save(req.toEntity(author));
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

        authorizeArticleAuthor(article);
      //authorize
        diaryRepository.delete(article);
    }

    
    @Transactional
    public Diary update(long id, UpdateDiaryRequest request) {
        Diary article = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        //authorize
        article.update(request.getEmotion(), request.getContent(), request.getIs_share(), request.getIs_comm());
        
        return article;
    }

  
    // 일기를 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Diary diary) {
    	String author = SecurityContextHolder.getContext().getAuthentication().getName();
    	if(!diary.getAuthor().equals(author))
    		throw new IllegalArgumentException("not authorized");
        }
   }