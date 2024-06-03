package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.domain.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SetUserInfoRequest {
    private String title;
    private String content;

    public UserInfo toEnity() {
        return UserInfo.builder()
                .title(title)
                .content(content)
                .build();
    }
}
