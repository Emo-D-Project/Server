package com.mydiary.my_diary_server.config.jwt;

import com.mydiary.my_diary_server.controller.UserController;
import com.mydiary.my_diary_server.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Autowired
    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expriedAt){
        Date now = new Date();
        String token = makeToken(new Date(now.getTime() + expriedAt.toMillis()), user);
        return token;
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user){
        Date now = new Date();

        logger.debug("HeaderType: " + Header.TYPE + "HeaderJWTType: " + Header.JWT_TYPE
        + "jwtpropertiesIssuer: " + jwtProperties.getIssuer() + "issuedat: " + now
        + "expiration: " + expiry + "userEmail: " + user.getEmail() + "userId: " + user.getId() + "jwtProperties: " + jwtProperties.getSecretKey());

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ : JWT
        // 내용 iss : apddl5@naver.com(propertises 파일에서 설정한 값)
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now) // 내용 iat : 현재 시간
                .setExpiration(expiry) // 내용 exp : expiry 멤버 변숫값
                .setSubject(user.getEmail()) // 내용 sub : 유저의 이메일
                .claim("id", user.getId()) // 클레임 id : 유저 ID
        // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();




    }

    public boolean validToken(String token){
        try{
            logger.debug("jwt parser start "
            + "\n token : " + token);

            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .parseClaimsJws(token);
            logger.debug("jwt parser complete");

            return true;
        } catch (Exception e){ // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token){
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));

        return new UsernamePasswordAuthenticationToken(new org.springframework.security.core.userdetails.User(claims.get("id").toString(), "", authorities), token, authorities);

    }

    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getUserID(String token){
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    private Claims getClaims(String token){
        return Jwts.parser() // 클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

}
