package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.dto.KakaoUserInfoDto;
import com.mydiary.my_diary_server.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        // userService와 authenticationManager의 Mock 객체를 주입
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testKakaoLogin() {
        // 필요한 Mock 데이터를 설정
        String authorizationHeader = "Bearer 4LoFlrE3rKXWhhzPn3n2aGKhoCO0VchJfaBrNL5nCj1zmwAAAYsngI_w"; // 적절한 토큰 값

        KakaoUserInfoDto kakaoUserInfoDto = new KakaoUserInfoDto(); // 적절한 값으로 초기화

        // UserController의 kakaoLogin 메서드 호출
        ResponseEntity<KakaoUserInfoDto> response = userController.kakaoLogin(authorizationHeader);

        // 예상한 결과와 실제 결과가 일치하는지 확인
        assertEquals(kakaoUserInfoDto, response.getBody());
    }

}