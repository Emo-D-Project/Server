//package com.mydiary.my_diary_server.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//public class KakaoLoginController {
//
//    @PostMapping("/processKakaoToken")
//    public ResponseEntity<String> processKakaoToken(@RequestBody String kakaoToken) {
//        // kakaoToken을 이용한 작업 수행
//        // 토큰 유효성 검증 및 사용자 인증 등의 작업을 진행할 수 있습니다.
//        if (isValidToken(kakaoToken)) {
//            // 토큰 검증 및 처리 성공
//            return ResponseEntity.ok("Kakao 토큰 검증 및 처리 성공");
//        } else {
//            // 토큰이 유효하지 않은 경우
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Kakao 토큰이 유효하지 않습니다.");
//        }
//    }
//
//    private boolean isValidToken(String kakaoToken) {
//        // 카카오 서버 또는 API를 사용하여 토큰 유효성을 검사
//        // 아래는 가상의 코드 예시이며, 실제로는 카카오에서 제공하는 도구를 사용해야 합니다.
//
//        try {
//            // 카카오 API를 호출하여 토큰 검증을 시도
//            // 예: KakaoAPI.isValidToken(kakaoToken)
//
//            // 검증에 성공하면 true 반환
//            if (KakaoAPI.isValidToken(kakaoToken)) {
//                return true;
//            } else {
//                return false;
//            }
//        } catch (Exception e) {
//            // API 호출 중 예외가 발생한 경우
//            // 검증에 실패하면 false 반환
//            return false;
//        }
//    }
//
//}