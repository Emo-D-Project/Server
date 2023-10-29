package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.data.entity.CalendarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
    // 추가적인 사용자 정의 쿼리 메서드를 정의할 수 있음
}

