package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.Message;
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

    @Operation(summary = "받은 쪽지 확인")
    @GetMapping()
    public ResponseEntity<List<MessageResponse>> findAllMyMessage(Principal principal) {
        List<MessageResponse> messages = messageService.findAllBySenderId(Long.parseLong(principal.getName()))
                .stream()
                .map(MessageResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(messages);
    }

    @Operation(summary = "쪽지 등록")
    @PostMapping()
    public ResponseEntity<MessageResponse> addMessage(@RequestBody AddMessageRequest request, Principal principal) {
        MessageResponse savedMessage = messageService.save(request, Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }

//    @Operation (summary = "쪽지 삭제")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteMessage(@PathVariable Long id) {
//        messageService.deleteMessage(id);
//        return ResponseEntity.ok()
//                .build();
//    }
}

