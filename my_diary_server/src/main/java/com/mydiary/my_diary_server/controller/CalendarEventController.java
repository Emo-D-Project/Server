package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.CalendarEvent;
import com.mydiary.my_diary_server.service.CalendarEventService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/calendar-events")
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    @Autowired
    public CalendarEventController(CalendarEventService calendarEventService) {
        this.calendarEventService = calendarEventService;
    }
    @Operation(summary = "캘린더이벤트 불러오기(get 요청 시 엔드포인트에 찾고자 하는 캘린더의 식별자 추가 ex. /api/calendar-events/{$calendarId}")
    @GetMapping("/{calendarId}")
    public ResponseEntity<CalendarEvent> getCalendarEventById(@PathVariable Long calendarId) {
        CalendarEvent event = calendarEventService.findCalendarEventByCalendarId(calendarId);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "캘린더 이벤트 저장하는 기능")
    @PostMapping()
    public ResponseEntity<CalendarEvent> saveOrUpdate(@RequestBody CalendarEvent updatedEvent) {
        CalendarEvent event = calendarEventService.findCalendarEventByCalendarId(updatedEvent.getId());
        if (event != null) {
            CalendarEvent updated = calendarEventService.updateCalendarEvent(updatedEvent);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "캘린더 이벤트 삭제하는 기능")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@PathVariable Long CalendarEventId) {
        calendarEventService.deleteCalendarEvent(CalendarEventId);
        return ResponseEntity.noContent().build();
    }
}

