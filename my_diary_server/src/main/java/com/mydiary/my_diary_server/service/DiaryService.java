package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Diary;

import com.mydiary.my_diary_server.dto.*;
import com.mydiary.my_diary_server.repository.DiaryRepository;
import org.springframework.stereotype.Service;



import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import com.mydiary.my_diary_server.service.UserService;

@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public Diary save(AddDiaryRequest req, String author) {
        return diaryRepository.save(new Diary(Long.parseLong(author), req.getContent(), req.getEmotion(), req.getIs_share(), req.getIs_comm() ));
    }

    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }
    
    public List<Diary> findMine(Long user_id)
    {
    	return diaryRepository.findByUserId(user_id);
    }

    public Diary findById(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public Integer getCount(Long user_id)
    {
    	return diaryRepository.findByUserId(user_id).size();
    }
    
    public LocalDateTime getFirst(Long user_id)
    {
    	return diaryRepository.findByUserId(user_id).get(0).getCreatedAt();
    }
    
    public String getMost(Long user_id)
    {
    	int[] counts = {0, 0, 0, 0};
    	List<Diary> list = diaryRepository.findByUserId(user_id);
    	int i;
    	for(i=0; i<list.size(); i++)
    	{
    		int hour = list.get(i).getCreatedAt().getHour();
    		if(hour>=00 && hour<06 )
    		{
    			counts[0]++;
    		}
    		else if(hour>=06 && hour<12)
    		{
    			counts[1]++;
    		}
    		else if(hour>=12 && hour<18)
    		{
    			counts[2]++;
    		}
    		else
    		{
    			counts[3]++;
    		}
    	}

    	int max = 0; int maxIndex = 0;
    	for(i=0; i>4; i++)
    	{
    		if(counts[i] > max)
    		{
    			maxIndex = i;
    			max = counts[i];
    		}
    	}
    	
    	switch(maxIndex)
    	{
    	case 0:
    		return "새벽(00~06)";
    	case 1:
    		return "아침(06~12)";
    	case 2:
    		return "오후(12~18)";
    	case 3:
    		return "밤(18~00)";
    	}
		return null;
    }


    public void delete(long id) {
        Diary article = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
      //authorize
        diaryRepository.delete(article);
    }

    /*
    public DiaryReportResponse getReport(Long user_id)
    {
    	List<Diary> diary = diaryRepository.findByUserId(user_id);
    	DiaryReportResponse result;
    	int i;
    	for(i=0; i<diary.size(); i++)
    	{
    		result = new DiaryReportResponse(diary.get(i).getCreatedAt());
    	}
    	
    }
    */
    
    public Diary searchPopular(Long user_id)
    {
    	List<Diary> list = diaryRepository.findByUserId(user_id);
    	int i, max=0;
    	Diary popular = new Diary();
    	
    	for(i=0; i<list.size(); i++)
    	{
    		if(list.get(i).getEmpathy() > max)
    			popular = list.get(i);
    	}
    	return popular;
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
    	if(!(diary.getUserId() == Long.parseLong(author)))
    		throw new IllegalArgumentException("not authorized");
        }
   }