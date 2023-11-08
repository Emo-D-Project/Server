package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Calendar;
import com.mydiary.my_diary_server.repository.CalendarRepository;
import jakarta.transaction.Transactional;
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
    public Calendar findCalendarByUserId(Long userId) {
        Optional<Calendar> calendarOptional = calendarRepository.findByUserId(userId);
        return calendarOptional.orElse(null);
    }

    @Transactional
    // 캘린더 업데이트 by ID
    public Calendar saveOrUpdate(Long userId, Calendar updatedCalendar) {
        Optional<Calendar> calendarOptional = calendarRepository.findByUserId(userId);
        if (calendarOptional.isPresent()) { // 기존 캘린더가 존재할 경우
            Calendar calendar = calendarOptional.get();
            calendar.setDate(updatedCalendar.getDate());

            return calendarRepository.save(calendar);
        }else{ // 기존 캘린더가 없을 경우
            return calendarRepository.save(updatedCalendar);
        }
    }


}

