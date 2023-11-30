package com.mydiary.my_diary_server.controller;

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

    @Operation (summary = "마이페이지에 등록한 정보들 불러오는 기능")
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
}
