package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Optional<List<Message>> findAllBySenderId(Long id);

    List<Message> findByReceiverIdOrSenderId(Long userId, Long userId1);
    // receiver_id와 sender_id가 주어진 myId와 otherUserId와 일치하는 메시지를 찾습니다.
    Optional<List<Message>> findByReceiverIdAndSenderIdOrReceiverIdAndSenderId(Long userId, Long otherUserId, Long otherUserId1, Long userId1);

}
