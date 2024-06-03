package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.dto.AnalysisResponse;
import com.mydiary.my_diary_server.dto.ReportDTO;
import com.mydiary.my_diary_server.dto.ReportResponse;
import com.mydiary.my_diary_server.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
    
    @PostMapping("/create/{comment}")
    public ResponseEntity<ReportDTO> createResponse(Principal principal, @PathVariable String comment)
    {
    	ReportDTO result = diaryService.createReport(principal.getName(), comment);
    	
    	return ResponseEntity.ok()
    			.body(result);
    }
    
    @GetMapping("/read")
    public ResponseEntity<List<ReportResponse>> getReport(Principal principal)
    {
    	List<ReportResponse> list = diaryService.getReports(principal.getName())
    			.stream()
    			.map(ReportResponse::new)
    			.toList();
    	
    	return ResponseEntity.ok()
    			.body(list);
    }
}
