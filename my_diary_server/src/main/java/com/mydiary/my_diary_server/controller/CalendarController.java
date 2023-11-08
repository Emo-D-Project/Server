package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.Calendar;
import com.mydiary.my_diary_server.service.CalendarService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/calendars")
public class CalendarController {

    private final CalendarService calendarService;

    @Autowired
    public CalendarController(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Operation (summary = "캘린더 정보를 받아오는 기능")
    @GetMapping()
    public ResponseEntity<Calendar> findCalendarById(@PathVariable Long userId) {
        Calendar calendar = calendarService.findCalendarByUserId(userId);
        if (calendar != null) {
            return ResponseEntity.ok(calendar);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "캘린더 정보 저장하는 기능")
    @PostMapping("/createOrUpdate")
    public ResponseEntity<Calendar> saveOrUpdateCalendar(@RequestBody Calendar calendar, Principal principal) {
        Calendar createdCalendar = calendarService.saveOrUpdate(Long.parseLong(principal.getName()),calendar);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCalendar);
    }
}

