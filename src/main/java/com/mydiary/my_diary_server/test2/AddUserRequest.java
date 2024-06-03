package com.mydiary.my_diary_server.test2;

import lombok.Data;

@Data
public class AddUserRequest {
    private String password;
    private String email;
}
