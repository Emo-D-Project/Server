package com.mydiary.my_diary_server.dto;

import lombok.*;

@Data
public class AddUserRequest {
    private String password;
    private String email;
}
