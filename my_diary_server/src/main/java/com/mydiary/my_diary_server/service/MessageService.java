package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Message;
import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.dto.AddMessageRequest;
import com.mydiary.my_diary_server.dto.MessageResponse;
import com.mydiary.my_diary_server.repository.MessageRepository;
import com.mydiary.my_diary_server.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    MessageService(MessageRepository messageRepository, UserRepository userRepository){
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }



    // 메시지 생성
    public MessageResponse save(AddMessageRequest request, Long senderId) {
        Optional<User> sender = userRepository.findById(senderId);
        Optional<User> receiver = userRepository.findById(request.getReceiverId());

        return new MessageResponse(messageRepository.save(Message.builder()
                .sender(sender.get())
                .receiver(receiver.get())
                .content(request.getContent())
                .sentAt(request.getSentAt())
                .build()));
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

    public List<Message> findAllBySenderId(Long id) {
        return messageRepository.findAllBySenderId(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }


}
