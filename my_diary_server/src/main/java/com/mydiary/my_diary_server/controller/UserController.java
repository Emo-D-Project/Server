package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.data.dto.KakaoUserInfoDto;
import com.mydiary.my_diary_server.data.dto.UserDTO;
import com.mydiary.my_diary_server.data.dto.UserLoginDTO;
import com.mydiary.my_diary_server.data.dto.UserResponseDTO;
import com.mydiary.my_diary_server.data.entity.OAuthType;
import com.mydiary.my_diary_server.data.entity.User;
import com.mydiary.my_diary_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.security.authentication.AuthenticationManager;



@RestController
@RequestMapping("/user")

public class UserController {

    private final UserService userService;
    @Value("${kakao.key}")
    private String kakaoKey;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService){this.userService = userService;}



    // 회원가입
    @PostMapping ("/register")
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserDTO userDTO){
        UserResponseDTO userResponseDTO = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<UserResponseDTO> loginUser(@RequestBody UserLoginDTO userLoginDTO) {
        UserResponseDTO userResponseDTO = userService.loginUser(userLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDTO);
    }

    @GetMapping("/auth/kakao")
    public String kakaoLogin(@RequestParam("token") String token) {
        // 토큰 처리 로직을 구현
        return joinKakaoUser(getKakaoUserInfo(token));
    }


    // 사용자 가입시켜주기
    private String joinKakaoUser(KakaoUserInfoDto kakaoUserInfo) {

        // 사용자가 이미 가입되어 있는지 확인
        String name = kakaoUserInfo.getProperties().getNickname();
        String email = kakaoUserInfo.getKakaoAccount().getEmail();
        boolean hasEmail = kakaoUserInfo.getKakaoAccount().getHasEmail();

        User kakaoLoginUser = User.builder().username(email + "_" + kakaoUserInfo.getId()).password(kakaoKey)
                .email(email).oauth(OAuthType.KAKAO).username(name).build();

        if (hasEmail) {
            User originUser = userService.checkUsername(kakaoLoginUser.getUsername());

            if (originUser == null) {
                userService.joinUser(kakaoLoginUser);
            }
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(kakaoLoginUser.getUsername(), kakaoKey));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/";
    }

    // 사용자 정보 받기
    private KakaoUserInfoDto getKakaoUserInfo(String kakaoToken) {

        HttpHeaders kakaoUserInfoHeader = new HttpHeaders();
        kakaoUserInfoHeader.add("Authorization", "Bearer " + kakaoToken);
        kakaoUserInfoHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(kakaoUserInfoHeader);
        RestTemplate kakaoUserInfoRest = new RestTemplate();
        ResponseEntity<KakaoUserInfoDto> kakaoUserInfoResponse = kakaoUserInfoRest.exchange(
                "https://kapi.kakao.com/v2/user/me", HttpMethod.POST, kakaoUserInfoRequest, KakaoUserInfoDto.class);
        return kakaoUserInfoResponse.getBody();
    }



}
