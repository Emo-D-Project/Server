package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.dto.SetUserInfoRequest;
import com.mydiary.my_diary_server.dto.UserInfoResponse;
import com.mydiary.my_diary_server.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import com.mydiary.my_diary_server.domain.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoApiController {

    UserInfoService userInfoService;

    @Autowired
    public UserInfoApiController(UserInfoService userInfoService){this.userInfoService = userInfoService;}

    @Operation (summary = "유저의 좋아하는 음악, 취미, mbti를 불러오는 기능")
    @GetMapping()
    public ResponseEntity<UserInfoResponse> findUserInfo(Principal principal){
        UserInfoResponse userInfoResponse = userInfoService.findById(Long.parseLong(principal.getName()));

        return ResponseEntity.ok()
                .body(userInfoResponse);
    }

    @Operation ( summary = "유저의 좋앙하는 음악, 취미, mbti 설정")
    @PostMapping()
    public ResponseEntity<UserInfoResponse> setUserInfo(@RequestBody SetUserInfoRequest request, Principal principal){
        UserInfoResponse userInfoResponse = userInfoService.saveOrUpdate(request.toEnity(), Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userInfoResponse);
    }
}
