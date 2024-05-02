package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.dto.CreateAccessTokenRequest;
import com.mydiary.my_diary_server.dto.CreateAccessTokenResponse;
import com.mydiary.my_diary_server.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class TokenApiController {
    private final TokenService tokenService;

    @Operation (summary = "리프레시 토큰을 이곳으로 보내면 검증 후 액세스 토큰 발급해주는 기능")
    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request){
        log.info("createNewAccessToken 컨트롤러 함수 실행");
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
