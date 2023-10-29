package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.data.entity.CalendarEvent;
import com.mydiary.my_diary_server.service.CalendarEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendar-events")
public class CalendarEventController {

    private final CalendarEventService calendarEventService;

    @Autowired
    public CalendarEventController(CalendarEventService calendarEventService) {
        this.calendarEventService = calendarEventService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarEvent> getCalendarEventById(@PathVariable Long id) {
        CalendarEvent event = calendarEventService.getCalendarEventById(id);
        if (event != null) {
            return ResponseEntity.ok(event);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<CalendarEvent> createCalendarEvent(@RequestBody CalendarEvent event) {
        CalendarEvent createdEvent = calendarEventService.createCalendarEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarEvent> updateCalendarEvent(@PathVariable Long id, @RequestBody CalendarEvent updatedEvent) {
        CalendarEvent event = calendarEventService.getCalendarEventById(id);
        if (event != null) {
            CalendarEvent updated = calendarEventService.updateCalendarEvent(id, updatedEvent);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarEvent(@PathVariable Long id) {
        calendarEventService.deleteCalendarEvent(id);
        return ResponseEntity.noContent().build();
    }
}

