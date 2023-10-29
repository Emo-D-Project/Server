package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Message;
import com.mydiary.my_diary_server.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // 메시지 생성
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    // 메시지 조회 by ID
    public Message getMessageById(Long id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        return messageOptional.orElse(null);
    }

    // 메시지 업데이트 by ID
    public Message updateMessage(Long id, Message updatedMessage) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setContent(updatedMessage.getContent());
            message.setSentAt(updatedMessage.getSentAt());
            // 추가 필드 업데이트

            return messageRepository.save(message);
        }
        return null;
    }

    // 메시지 삭제 by ID
    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }
}
