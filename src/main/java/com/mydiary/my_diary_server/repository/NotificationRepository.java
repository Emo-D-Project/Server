package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByTargetUserId(long l);
    // 특별한 메소드가 필요하지 않다면 여기에 추가할 필요가 없습니다.
}

