package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.data.dto.UserDTO;
import com.mydiary.my_diary_server.data.dto.UserLoginDTO;
import com.mydiary.my_diary_server.data.dto.UserResponseDTO;
import com.mydiary.my_diary_server.data.entity.User;

public interface UserService {
    UserResponseDTO registerUser(UserDTO userDTO);

    UserResponseDTO loginUser(UserLoginDTO userLoginDTO);

    User checkUsername(String username);

    User joinUser(User user);
}
