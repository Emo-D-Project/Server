package com.mydiary.my_diary_server.data.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    private String password;
    private String email;
}
