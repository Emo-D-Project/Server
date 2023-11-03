package com.mydiary.my_diary_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mydiary.my_diary_server.domain.UserInfo;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SetUserInfoRequest {
    private String music;
    private String hobby;
    private String mbti;

    public UserInfo toEnity() {
        return UserInfo.builder()
                .music(music)
                .hobby(hobby)
                .mbti(mbti)
                .build();
    }
}
