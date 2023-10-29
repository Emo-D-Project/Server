//package com.mydiary.my_diary_server.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//public class WebSecurityConfig {
//
//    // 스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure(){
//        return (web) -> web.ignoring()
//                .requestMatchers(toH2Console())
//                .requestMatchers("/static/**");
//    }
//
//
//}
