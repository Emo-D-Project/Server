package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.UserInfo;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private String title;
    private String content;

    public UserInfoResponse(UserInfo userinfo){
        this.title = userinfo.getTitle();
        this.content = userinfo.getContent();
    }
}
