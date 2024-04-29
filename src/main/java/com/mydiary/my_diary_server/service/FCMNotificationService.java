package com.mydiary.my_diary_server.service;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.dto.FCMNotificationRequestDto;
import com.mydiary.my_diary_server.repository.MessageRepository;
import com.mydiary.my_diary_server.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    public String sendNotificationByToken(FCMNotificationRequestDto requestDto) {
        Optional<User> user = userRepository.findById(requestDto.getTargetUserId());

        if (user.isPresent()) {
            if (user.get().getFirebaseToken() != null) {
                String body = requestDto.getBody();
                // body에 현재 시각을 추가
                body += " \n보낸 시간 : " + LocalDateTime.now().toString();

                Notification notification = Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(body)
                        .build();
                log.info("notification");

                Message message = Message.builder()
                        .setToken(user.get().getFirebaseToken())
                        .setNotification(notification)
                        .build();
                log.info(message.toString());


                try {
                    firebaseMessaging.send(message);
                    return "알림을 성공적으로 전송했습니다. targetUserId=" + requestDto.getTargetUserId();
                } catch (FirebaseMessagingException e) {
                    log.error("Firebase 알림 전송 중 오류 발생:", e);
                    return "알림 보내기 실패 targetUserId=" + requestDto.getTargetUserId();
                }
            } else {
                return "해당 유저가 존재하지 않습니다. targetUserId=" + requestDto.getTargetUserId();
            }
        }
        return "sendNotificationByToken - 예상치못한 에러 발생";
    }
}

