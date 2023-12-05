package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.ChatRoom;
import com.mydiary.my_diary_server.domain.Message;
import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.dto.AddMessageRequest;
import com.mydiary.my_diary_server.dto.MessageResponse;
import com.mydiary.my_diary_server.repository.MessageRepository;
import com.mydiary.my_diary_server.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

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
                .senderId(sender.get().getId())
                .receiverId(receiver.get().getId())
                .content(request.getContent())
                .sentAt(request.getSentAt())
                .isRead(false) // 쪽지 초기값은 안읽은 모두 
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

    @Transactional
    public List<Message> findChats(Long otherUserId, Long userId) {
        List<Message> messages = messageRepository.findByReceiverIdAndSenderIdOrReceiverIdAndSenderId(userId, otherUserId, otherUserId, userId)
                .orElseThrow(() -> new IllegalArgumentException("not found find chats / id : " + userId + "otherUserId: " + otherUserId));
        for (Message message : messages) {
            if(message.getReceiverId() == userId){
                message.setRead(true);
            }
        }
        
        return messages;
    }


    public List<ChatRoom> getAllChatRooms(Long userId) {
        // 사용자의 id와 관련된 메시지를 찾아 채팅방으로 반환
        List<Message> userMessages = messageRepository.findByReceiverIdOrSenderId(userId, userId);

        // 각 채팅방별로 마지막 메시지와 대화 상대를 가져옴
        Map<Long, ChatRoom> chatRoomsMap = new HashMap<>();
        for (Message message : userMessages) {
            Long otherUserId = (message.getReceiverId().equals(userId)) ? message.getSenderId() : message.getReceiverId();

            ChatRoom chatRoom = chatRoomsMap.get(otherUserId);
            if (chatRoom == null) {
                chatRoom = new ChatRoom(otherUserId, "Room with User " + otherUserId, message.isRead());
            }
            // 마지막 메시지 업데이트
            chatRoom.setLastMessage(message.getContent());
            chatRoom.setLastMessageSentAt(message.getSentAt());
            chatRoom.setName(userRepository.findById(otherUserId).get().getUsername());

            if(message.getReceiverId()==userId && !message.isRead()){
                chatRoom.setRead(false);
            }else{
                chatRoom.setRead(true);
            }

            chatRoomsMap.put(otherUserId, chatRoom);
        }

        // Map의 값들을 List로 반환
        return new ArrayList<>(chatRoomsMap.values());
    }

    public void deleteAllMessagesByUserId(Long userId) {
        // 사용자의 id와 관련된 메시지를 찾아 삭제
        List<Message> userMessages = messageRepository.findByReceiverIdOrSenderId(userId, userId);

        messageRepository.deleteAll(userMessages);
    }
}
