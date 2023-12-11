package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.ChatRoom;
import com.mydiary.my_diary_server.dto.AddMessageRequest;
import com.mydiary.my_diary_server.dto.MessageResponse;
import com.mydiary.my_diary_server.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api/messages")
public class MessageApiController {

    private final MessageService messageService;

    @Autowired
    MessageApiController(MessageService messageService){this.messageService = messageService;}

    @GetMapping("/chat/{otherUserId}")
    @Operation(summary = "채팅목록 불러오기")
    public ResponseEntity<List<MessageResponse>> findChats(@PathVariable Long otherUserId, Principal principal) {
        List<MessageResponse> messages = messageService.findChats(otherUserId, Long.parseLong(principal.getName()))
                .stream()
                .map(MessageResponse::new)
                .toList();

        // 자신의 메시지인지 확인해주는 코드
        for(MessageResponse messageResponse : messages){
            messageResponse.setMyMessage(messageResponse.getSenderId() == Long.parseLong(principal.getName()));
            if(messageResponse.getReceiverId().equals(Long.parseLong(principal.getName()))){

            }
        }

        return ResponseEntity.ok()
                .body(messages);
    }


    @PostMapping()
    @Operation(summary = "쪽지 등록")
    public ResponseEntity<MessageResponse> addMessage(@RequestBody AddMessageRequest request, Principal principal) {
        MessageResponse savedMessage = messageService.save(request, Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

    @GetMapping("/chatList")
    @Operation(summary = "채팅방 목록 불러오기")
    public ResponseEntity<List<ChatRoom>> getAllChatRooms(Principal principal) {
        List<ChatRoom> chatRooms = messageService.getAllChatRooms(Long.parseLong(principal.getName()));
        return ResponseEntity.ok(chatRooms);
    }

    @DeleteMapping("/deleteAll")
    @Operation ( summary = "자신 쪽지 모두 삭제")
    public ResponseEntity deleteAllMyMessage(Principal principal){
        Long userId = Long.parseLong(principal.getName());
        messageService.deleteAllMessagesByUserId(userId);
        return ResponseEntity.ok("All messages deleted for user with ID: " + userId);

    }


}