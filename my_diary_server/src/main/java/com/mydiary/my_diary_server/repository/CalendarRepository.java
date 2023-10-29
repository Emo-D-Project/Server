package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.data.entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
}

