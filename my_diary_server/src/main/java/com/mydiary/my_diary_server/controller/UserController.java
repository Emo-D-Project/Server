package com.mydiary.my_diary_server.controller;

import com.mydiary.my_diary_server.config.jwt.JwtProperties;
import com.mydiary.my_diary_server.config.jwt.TokenProvider;
import com.mydiary.my_diary_server.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.mydiary.my_diary_server.config.oauth.OAuth2SuccessHandler;
import com.mydiary.my_diary_server.config.oauth.OAuth2UserCustomService;
import com.mydiary.my_diary_server.domain.RefreshToken;
import com.mydiary.my_diary_server.dto.KakaoUserInfoDto;
import com.mydiary.my_diary_server.domain.OAuthType;
import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.repository.RefreshTokenRepository;
import com.mydiary.my_diary_server.service.RefreshTokenService;
import com.mydiary.my_diary_server.service.UserService;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


@RestController
@RequestMapping("/user")

public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(30);
    public static final Duration ACCESS_TOKEN_DURATION = Duration.ofDays(14);
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtProperties jwtProperties;


    private final UserService userService;
    @Value("${kakao.key}")
    private String kakaoKey;



    @Autowired
    public UserController(UserService userService){this.userService = userService;}


    // 카카오 로그인
    @Operation (summary = "헤더에 kakaoAccessToken 을 넣어서 보내면 서버의 JWT 토큰 발급 ")
    @GetMapping("/auth/kakao")
    public ResponseEntity<KakaoUserInfoDto> kakaoLogin(@Parameter(hidden = false) @RequestHeader(required = false)  String kakaoAccessToken) {
        String token = kakaoAccessToken.replace("Bearer ", "");

        // 토큰 처리 로직을 구현
        KakaoUserInfoDto userInfo = joinKakaoUser(getKakaoUserInfo(token));
        return ResponseEntity.ok(userInfo);
    }


    // 사용자 가입시켜주기
    KakaoUserInfoDto joinKakaoUser(KakaoUserInfoDto kakaoUserInfo) {

        String accessToken;
        String refreshToken;

        // 사용자가 이미 가입되어 있는지 확인
        String name = kakaoUserInfo.getProperties().getNickname();
        String email = kakaoUserInfo.getKakaoAccount().getEmail();
        boolean hasEmail = kakaoUserInfo.getKakaoAccount().getHasEmail();

        User kakaoLoginUser = User.builder().password(email + "_" + kakaoUserInfo.getId())
                .email(email).oauth(OAuthType.KAKAO).username(name).build();

        if (hasEmail) {
            User originUser = userService.checkUsername(kakaoLoginUser.getUsername());
            logger.debug("hasEmail == True");

            if (originUser == null) {
                User savedUser = userService.joinUser(kakaoLoginUser);
                accessToken = tokenProvider.generateToken(savedUser, ACCESS_TOKEN_DURATION);
                refreshToken = tokenProvider.generateToken(savedUser, REFRESH_TOKEN_DURATION);
                saveRefreshToken(savedUser.getId(), refreshToken);
                kakaoUserInfo.setAccessToken(accessToken);
                kakaoUserInfo.setRefreshToken(refreshToken);

                try{
                    logger.debug("saved jwt parser start " );

                    Jwts.parser()
                            .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                            .parseClaimsJws(accessToken);
                    logger.debug("jwt parser complete");

                } catch (Exception e){ // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
                    logger.debug("jwt parser fail " );
                }

            }
            else{
                logger.debug(("originUser is not null"));
                accessToken = tokenProvider.generateToken(originUser, ACCESS_TOKEN_DURATION);
                refreshToken = tokenProvider.generateToken(originUser, REFRESH_TOKEN_DURATION);

                try{
                    logger.debug("origin jwt parser start " );
                    logger.debug("usercontroll token: " + accessToken + "\n");


                    Jwts.parser()
                            .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                            .parseClaimsJws(accessToken);
                    logger.debug("jwt parser complete");

                } catch (Exception e){ // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
                    logger.debug("jwt parser fail " );
                }

                saveRefreshToken(originUser.getId(), refreshToken);

                kakaoUserInfo.setAccessToken(accessToken);
                kakaoUserInfo.setRefreshToken(refreshToken);


            }


        }

        return kakaoUserInfo;
    }

    // 사용자 정보 받기
    KakaoUserInfoDto getKakaoUserInfo(String kakaoToken) {

        HttpHeaders kakaoUserInfoHeader = new HttpHeaders();
        kakaoUserInfoHeader.add("Authorization", "Bearer " + kakaoToken);
        kakaoUserInfoHeader.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(kakaoUserInfoHeader);
        RestTemplate kakaoUserInfoRest = new RestTemplate();
        ResponseEntity<KakaoUserInfoDto> kakaoUserInfoResponse = kakaoUserInfoRest.exchange(
                "https://kapi.kakao.com/v2/user/me", HttpMethod.GET, kakaoUserInfoRequest, KakaoUserInfoDto.class);
        return kakaoUserInfoResponse.getBody();
    }

    private void saveRefreshToken(Long userId, String newRefreshToken){
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));

        refreshTokenRepository.save(refreshToken);
    }



}
