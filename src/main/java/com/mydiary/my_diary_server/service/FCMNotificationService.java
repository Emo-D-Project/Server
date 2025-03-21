package com.mydiary.my_diary_server.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.mydiary.my_diary_server.domain.Notification;
import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.dto.FCMNotificationRequestDto;
import com.mydiary.my_diary_server.repository.NotificationRepository;
import com.mydiary.my_diary_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
@RequiredArgsConstructor
public class FCMNotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository; // NotificationRepository 주입

    public String sendNotificationByToken(FCMNotificationRequestDto requestDto, Long senderId) {
        Optional<User> user = userRepository.findById(requestDto.getTargetUserId());

        if (user.isPresent()) {
            if (user.get().getFirebaseToken() != null) {

                String body = requestDto.getBody();

                com.google.firebase.messaging.Notification notification = com.google.firebase.messaging.Notification.builder()
                        .setTitle(requestDto.getTitle())
                        .setBody(body)
                        .build();

                Message message = Message.builder()
                        .setToken(user.get().getFirebaseToken())
                        .setNotification(notification)
                        .putData("sendTime", LocalDateTime.now().toString())
                        .putData("senderId", senderId.toString())
                        .putData("title", requestDto.getTitle())
                        .putData("postId", requestDto.getPostId().toString())
                        .build();


                try {
                    firebaseMessaging.send(message);

                    // 메시지 전송에 성공하면 데이터베이스에 저장
                    saveNotificationToDatabase(requestDto.getTargetUserId(), requestDto.getTitle(), body, LocalDateTime.now());

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

    // 알림 내용과 보낸 시간을 데이터베이스에 저장하는 메소드
    private void saveNotificationToDatabase(Long targetUserId, String title, String body, LocalDateTime sentTime) {
        // 저장할 알림 생성
        Notification notificationEntity = new Notification(targetUserId, title, body, sentTime);

        // 데이터베이스에 저장
        notificationRepository.save(notificationEntity);
    }

    // 내가 받은 알람 목록 조회
    public List<Notification> getNotificationList(Long targetUserId) {
        return notificationRepository.findAllByTargetUserId(targetUserId);
    }
}