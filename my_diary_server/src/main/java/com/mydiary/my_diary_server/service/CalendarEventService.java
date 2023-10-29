package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.data.entity.CalendarEvent;
import com.mydiary.my_diary_server.repository.CalendarEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarEventService {

    private final CalendarEventRepository calendarEventRepository;

    @Autowired
    public CalendarEventService(CalendarEventRepository calendarEventRepository) {
        this.calendarEventRepository = calendarEventRepository;
    }

    // 캘린더 이벤트 생성
    public CalendarEvent createCalendarEvent(CalendarEvent event) {
        return calendarEventRepository.save(event);
    }

    // 캘린더 이벤트 조회 by ID
    public CalendarEvent getCalendarEventById(Long id) {
        Optional<CalendarEvent> eventOptional = calendarEventRepository.findById(id);
        return eventOptional.orElse(null);
    }

    // 캘린더 이벤트 업데이트 by ID
    public CalendarEvent updateCalendarEvent(Long id, CalendarEvent updatedEvent) {
        Optional<CalendarEvent> eventOptional = calendarEventRepository.findById(id);
        if (eventOptional.isPresent()) {
            CalendarEvent event = eventOptional.get();
            event.setEventName(updatedEvent.getEventName());
            event.setDate(updatedEvent.getDate());
            // 추가 필드 업데이트

            return calendarEventRepository.save(event);
        }
        return null;
    }

    // 캘린더 이벤트 삭제 by ID
    public void deleteCalendarEvent(Long id) {
        calendarEventRepository.deleteById(id);
    }
}
