package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    Optional<CalendarEvent> findByCalendarId(Long calendarId);
    // 추가적인 사용자 정의 쿼리 메서드를 정의할 수 있음
}

