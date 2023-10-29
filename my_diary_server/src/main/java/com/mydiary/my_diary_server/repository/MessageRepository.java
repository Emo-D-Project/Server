package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}