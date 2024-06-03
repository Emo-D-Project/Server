package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.domain.UserInfo;
import lombok.Getter;

@Getter
public class UserInfoResponse {
    private String title;
    private String content;
    private Long userId;

    public UserInfoResponse(UserInfo userinfo){
        this.title = userinfo.getTitle();
        this.content = userinfo.getContent();
        this.userId = userinfo.getUser().getId();
    }
}
