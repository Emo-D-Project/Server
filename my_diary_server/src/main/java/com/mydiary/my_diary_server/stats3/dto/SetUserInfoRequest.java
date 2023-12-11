package com.mydiary.my_diary_server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.mydiary.my_diary_server.domain.UserInfo;

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
