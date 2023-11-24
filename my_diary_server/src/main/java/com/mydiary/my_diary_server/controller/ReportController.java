package com.mydiary.my_diary_server.controller;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mydiary.my_diary_server.dto.AnalysisResponse;
import com.mydiary.my_diary_server.service.DiaryService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final DiaryService diaryService;
    
    @GetMapping("/analysis")
    public ResponseEntity<AnalysisResponse> getAnalysis(Principal principal)
    {
    	AnalysisResponse result = diaryService.getAnalysis(principal.getName());
    	
    	return ResponseEntity.ok()
    			.body(result);
    }
}
