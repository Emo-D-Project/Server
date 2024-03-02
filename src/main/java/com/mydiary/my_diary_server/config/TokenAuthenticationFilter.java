package com.mydiary.my_diary_server.config;

import com.mydiary.my_diary_server.config.jwt.TokenProvider;
import com.mydiary.my_diary_server.config.oauth.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.mydiary.my_diary_server.config.oauth.OAuth2SuccessHandler;
import com.mydiary.my_diary_server.config.oauth.OAuth2UserCustomService;
import com.mydiary.my_diary_server.controller.UserController;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Autowired
    OAuth2SuccessHandler oAuth2SuccessHandler;
    @Autowired
    OAuth2UserCustomService oAuth2UserCustomService;

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {



        // 요청 헤더의 Authorization 키의 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 가져온 값에서 접두사 제거
        String token = getAccessToken(authorizationHeader);
        // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보를 설정
        if (tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            logger.debug("token's principal: " + authentication.getPrincipal());
            logger.debug("token's authorities: " + authentication.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getAccessToken(String authorizationHeader){
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }


}
