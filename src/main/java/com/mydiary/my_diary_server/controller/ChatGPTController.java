package com.mydiary.my_diary_server.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydiary.my_diary_server.dto.DiaryResponse;
import com.mydiary.my_diary_server.dto.MessageResponseDTO;
import com.mydiary.my_diary_server.dto.MessageResponseDTO.Content;
import com.mydiary.my_diary_server.dto.MessagesListResponseDTO;
import com.mydiary.my_diary_server.dto.aiReportDTO;
import com.mydiary.my_diary_server.dto.aiReportResponse;
import com.mydiary.my_diary_server.service.DiaryService;
import com.mydiary.my_diary_server.service.GptService;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/aiReport")
public class ChatGPTController {

    private final GptService gptService;
    private final DiaryService diaryService;
        
    @PostMapping("/msg")
    public aiReportDTO send(Principal principal) throws Exception
    {    	
    	List<String> result = new ArrayList<String>();
    	aiReportDTO report = diaryService.findWeekPlusNull(Long.parseLong(principal.getName()));
    	
    	List<DiaryResponse> diaries = diaryService.findWeek(Long.parseLong(principal.getName()))
                .stream()
                .map(DiaryResponse::new)
                .toList();

    	for(DiaryResponse res : diaries)
    	{
    		result.add(res.getContent());
    	}
    	   	
    	String param = String.join("," , result);
    	report.setPositive(
    			String.valueOf(
    					gptService.getAssistantMsg(
    							param + " 각각 문장들에서 가장 기분이 좋은 사건을 하나만 나열해 줘.")));
    
    	param = String.join("," , result);
    	report.setNegative(
    			String.valueOf(
    					gptService.getAssistantMsg(
    							param + " 각각 문장들에서 가장 기분이 나쁜 사건을 하나만 나열해 줘.")));
    	

    	report.setEmotion(String.valueOf(gptService.getAssistantMsg(param + " 이것들을 요약한걸 smile, flutter, angry, annoying, tired, sad, calmness 중 하나로 정해줘")));
    	
    	if(report.getMon() != null)
    	{
    			param = report.getMon();
    			report.setMon(String.valueOf(gptService.getAssistantMsg(param + " 이 문장을 한줄로 요약해줘.")));
    	}
    	if(report.getTue() != null)
    	{
    			param = report.getTue();
    			report.setTue(String.valueOf(gptService.getAssistantMsg(param + " 이 문장을 한줄로 요약해줘.")));
    	}
    	if(report.getWed() != null)
    	{
    			param = report.getWed();
    			report.setWed(String.valueOf(gptService.getAssistantMsg(param + " 이 문장을 한줄로 요약해줘.")));
    	}
    	if(report.getThu() != null)
    	{
    			param = report.getWed();
    			report.setThu(String.valueOf(gptService.getAssistantMsg(param + " 이 문장을 한줄로 요약해줘.")));
    	}
    	if(report.getFri() != null)
    	{
    			param = report.getWed();
    			report.setThu(String.valueOf(gptService.getAssistantMsg(param + " 이 문장을 한줄로 요약해줘.")));
    	}
    	if(report.getSat() != null)
    	{
    			param = report.getSat();
    			report.setSat(String.valueOf(gptService.getAssistantMsg(param + " 이 문장을 한줄로 요약해줘.")));
    	}
    	if(report.getSun() != null)
    	{
    			param = report.getSun();
    			report.setSun(String.valueOf(gptService.getAssistantMsg(param + " 이 문장을 한줄로 요약해줘.")));
    	}
    	

    	
    	return report;
    }
    
    @GetMapping("/read")
    public aiReportResponse read(Principal principal) throws Exception
    {
    	aiReportResponse res = new aiReportResponse();
    	aiReportDTO dto = send(principal);
    	List<String> list = new ArrayList<String>();
    	

    	res.setEmotion(dto.getEmotion().replaceAll("\"", ""));
    	res.setPositiveEvent(dto.getPositive().replaceAll("\"", ""));
    	res.setNegativeEvent(dto.getNegative().replaceAll("\"", ""));
    	
    	if(dto.getMon() != null)
    	{
    		list.add(dto.getMon().replaceAll("\"", ""));
    	}
    	else
    	{
    		list.add("");
    	}
    	
    	if(dto.getTue() != null)
    	{
    		list.add(dto.getTue().replaceAll("\"", ""));
    	}
    	else
    	{
    		list.add("");
    	}

    	if(dto.getWed() != null)
    	{
    		list.add(dto.getWed().replaceAll("\"", ""));
    	}
    	else
    	{
    		list.add("");
    	}
    	if(dto.getThu() != null)
    	{
    		list.add(dto.getThu().replaceAll("\"", ""));
    	}
    	else
    	{
    		list.add("");
    	}

    	if(dto.getFri() != null)
    	{
    		list.add(dto.getFri().replaceAll("\"", ""));
    	}
    	else
    	{
    		list.add("");
    	}
    	
    	if(dto.getSat() != null)
    	{
    		list.add(dto.getSat().replaceAll("\"", ""));
    	}
    	else
    	{
    		list.add("");
    	}
    	
    	if(dto.getSun() != null)
    	{
    		list.add(dto.getSun().replaceAll("\"", ""));
    	}
    	else
    	{
    		list.add("");
    	}
    	
    	res.setSummary(list);
    	
    	return res;
    }
}