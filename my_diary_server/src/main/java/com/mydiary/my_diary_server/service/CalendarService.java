package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Calendar;
import com.mydiary.my_diary_server.repository.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    // 캘린더 생성
    public Calendar createCalendar(Calendar calendar) {
        return calendarRepository.save(calendar);
    }

    // 캘린더 조회 by ID
    public Calendar getCalendarById(Long id) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);
        return calendarOptional.orElse(null);
    }

    // 캘린더 업데이트 by ID
    public Calendar updateCalendar(Long id, Calendar updatedCalendar) {
        Optional<Calendar> calendarOptional = calendarRepository.findById(id);
        if (calendarOptional.isPresent()) {
            Calendar calendar = calendarOptional.get();
            calendar.setName(updatedCalendar.getName());
            calendar.setColor(updatedCalendar.getColor());
            calendar.setNotificationEnabled(updatedCalendar.isNotificationEnabled());
            calendar.setTimeZone(updatedCalendar.getTimeZone());
            // 추가 필드 업데이트

            return calendarRepository.save(calendar);
        }
        return null;
    }

    // 캘린더 삭제 by ID
    public void deleteCalendar(Long id) {
        calendarRepository.deleteById(id);
    }
}

