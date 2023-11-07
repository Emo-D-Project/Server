package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.UserInfo;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private String music;
    private String hobby;
    private String mbti;

    public UserInfoResponse(UserInfo userinfo){
        this.music = userinfo.getMusic();
        this.hobby = userinfo.getHobby();
        this.mbti = userinfo.getMbti();
    }
}
