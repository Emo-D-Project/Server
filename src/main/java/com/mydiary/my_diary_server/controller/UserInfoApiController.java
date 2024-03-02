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

    @Operation (summary = "자신의 마이페이지에 등록한 자기 소개 정보를 불러오는 기능")
    @GetMapping("my")
    public ResponseEntity<String> findMyDescription(Principal principal){
        List<UserInfoResponse> userInfoResponse = userInfoService.findAllById(Long.parseLong(principal.getName()));

        for (UserInfoResponse response : userInfoResponse) {
            if(response.getTitle().equals("자기 소개")){
                return ResponseEntity.ok().body(response.getContent());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @Operation (summary = "아이디로 마이페이지에 등록한 자기 소개 정보를 불러오는 기능")
    @GetMapping("description/{userId}")
    public ResponseEntity<String> findDescriptionById(Long userId){
        List<UserInfoResponse> userInfoResponse = userInfoService.findAllById(userId);

        for (UserInfoResponse response : userInfoResponse) {
            if(response.getTitle().equals("자기 소개")){
                return ResponseEntity.ok().body(response.getContent());
            }
        }

        return ResponseEntity.notFound().build();
    }

    @Operation ( summary = "마이페이지에 자기 소개 정보 등록하는 기능")
    @PostMapping("my/description")
    public ResponseEntity<Void> setUserDescription(@RequestBody String description, Principal principal){
        SetUserInfoRequest request = new SetUserInfoRequest();
        request.setTitle("자기 소개");
        request.setContent(description);

        UserInfoResponse userInfoResponse = userInfoService.saveOrUpdate(request.toEnity(), Long.parseLong(principal.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation (summary = "아이디로 마이페이지에 등록한 정보들 불러오는 기능")
    @GetMapping("/{userId}")
    public ResponseEntity<List<UserInfoResponse>> findUserInfoById(@PathVariable Long userId){
        List<UserInfoResponse> userInfoResponse = userInfoService.findAllById(userId);
        // 자기 소개 부분만 빼고 리턴할 수 있게 해주는 코드
        // userInfoResponse.removeIf(response -> response.getTitle().equals("자기 소개"));
        return ResponseEntity.ok()
                .body(userInfoResponse);
    }


}
