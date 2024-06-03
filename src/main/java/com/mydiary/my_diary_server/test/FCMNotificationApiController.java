package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.domain.Notification;
import com.mydiary.my_diary_server.dto.FCMNotificationRequestDto;
import com.mydiary.my_diary_server.service.FCMNotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name= "Notification", description = "FCM Notification 관련 api")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class FCMNotificationApiController {

    private final FCMNotificationService fcmNotificationService;



    // 알림 보내기
    @Operation(summary = "알림 보내기")
    @PostMapping()
    public String sendNotificationByToken(@RequestBody FCMNotificationRequestDto requestDto, Principal principal){
        return fcmNotificationService.sendNotificationByToken(requestDto, Long.parseLong(principal.getName()));
    }

    // 내가 받은 알람 목록 조회
    @Operation(summary = "내가 받은 알림 목록 조회")
    @GetMapping()
    public List<Notification> getNotificationList(Principal principal){
        List<Notification> list = fcmNotificationService.getNotificationList(Long.parseLong(principal.getName()));
        return list;
    }






}
