package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findAllBySenderId(Long id);

}
