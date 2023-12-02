package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.domain.UserInfo;
import com.mydiary.my_diary_server.dto.SetUserInfoRequest;
import com.mydiary.my_diary_server.dto.UserInfoResponse;
import com.mydiary.my_diary_server.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/userInfo")
public class UserInfoApiController {

    UserInfoService userInfoService;

    @Autowired
    public UserInfoApiController(UserInfoService userInfoService){this.userInfoService = userInfoService;}

    @Operation (summary = "자신의 마이페이지에 등록한 정보들 불러오는 기능")
    @GetMapping()
    public ResponseEntity<List<UserInfoResponse>> findUserInfo(Principal principal){
        List<UserInfoResponse> userInfoResponse = userInfoService.findAllById(Long.parseLong(principal.getName()));

        return ResponseEntity.ok()
                .body(userInfoResponse);
    }

    @Operation ( summary = "마이페이지에 정보 등록하는 기능")
    @PostMapping()
    public ResponseEntity<UserInfoResponse> setUserInfo(@RequestBody SetUserInfoRequest request, Principal principal){
        UserInfoResponse userInfoResponse = userInfoService.saveOrUpdate(request.toEnity(), Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userInfoResponse);
    }

    @Operation ( summary = "마이페이지에 자기 소개 정보 등록하는 기능")
    @PostMapping("my/description")
    public ResponseEntity<UserInfoResponse> setUserDescription(@RequestBody String description, Principal principal){
        SetUserInfoRequest request = new SetUserInfoRequest();
        request.setTitle("자기 소개");
        request.setContent(description);

        UserInfoResponse userInfoResponse = userInfoService.saveOrUpdate(request.toEnity(), Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userInfoResponse);
    }

    @Operation (summary = "아이디로 마이페이지에 등록한 정보들 불러오는 기능")
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserInfoResponse>> findUserInfoById(@PathVariable Long userId){
        List<UserInfoResponse> userInfoResponse = userInfoService.findAllById(userId);

        userInfoResponse.removeIf(response -> response.getTitle().equals("자기 소개"));

        return ResponseEntity.ok()
                .body(userInfoResponse);
    }


}
