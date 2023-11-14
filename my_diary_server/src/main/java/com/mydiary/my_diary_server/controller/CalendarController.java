package com.mydiary.my_diary_server.controller;


import com.mydiary.my_diary_server.service.DiaryService;
import com.mydiary.my_diary_server.dto.*;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/calendars")
public class CalendarController {
    private final DiaryService diaryService;

    
    @Operation (summary = "캘린더 정보를 받아오는 기능")
    @GetMapping("/date/{date}")
    public ResponseEntity<List<CalendarResponse>> getCalender(
    		@PathVariable(value="date") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") LocalDateTime date, Principal principal) {
    	CalendarInfo info = diaryService.setCalendar(date, principal.getName());
    	
    	List<CalendarResponse> res = diaryService.getCalendar(info)
    			.stream()
    			.map(CalendarResponse::new)
    			.toList();
    	
    	return ResponseEntity.ok()
    			.body(res);
    }
}


