package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.CalendarEvent;
import com.mydiary.my_diary_server.repository.CalendarEventRepository;
import io.swagger.v3.oas.annotations.Operation;
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

    // 캘린더 이벤트 조회 by ID
    @Operation ( summary = "조회하고자 하는 캘린더의 식별자를 보내면 해당 월의 이벤트를 반환")
    public CalendarEvent findCalendarEventByCalendarId(Long calendarId) {
        Optional<CalendarEvent> eventOptional = calendarEventRepository.findByCalendarId(calendarId);
        return eventOptional.orElse(null);
    }

    // 캘린더 이벤트 업데이트 by ID
    @Operation ( summary = "캘린더 이벤트를 저장 (보내는 데이터에 저장할 캘린더의 식별자가 꼭 있어야됨 ex. 1월의 이벤트를 저장하고자 할 " +
            "때 요청하는 데이터에 1월의 식별자가 포함되어 있어야됨) ")
    public CalendarEvent updateCalendarEvent(CalendarEvent updatedEvent) {
        Optional<CalendarEvent> eventOptional = calendarEventRepository.findByCalendarId(updatedEvent.getCalendarId());
        if (eventOptional.isPresent()) { // 기존 이벤트가 있을 경우
            CalendarEvent event = eventOptional.get();
            event.setEventName(updatedEvent.getEventName());
            event.setDate(updatedEvent.getDate());
            // 추가 필드 업데이트

            return calendarEventRepository.save(event);
        }else { // 기존의 이벤트가 없을경우
            return calendarEventRepository.save(updatedEvent);
        }
    }

    // 캘린더 이벤트 삭제 by ID
    @Operation (summary = "캘린더 이벤트를 삭제")
    public void deleteCalendarEvent(Long CalendarEventId) {
        calendarEventRepository.deleteById(CalendarEventId);
    }
}
