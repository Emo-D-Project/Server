package com.mydiary.my_diary_server.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private String password;
    private String email;
}
