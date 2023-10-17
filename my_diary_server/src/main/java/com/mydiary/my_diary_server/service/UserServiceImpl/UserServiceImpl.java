package com.mydiary.my_diary_server.service.UserServiceImpl;

import ch.qos.logback.classic.encoder.JsonEncoder;
import com.mydiary.my_diary_server.data.dao.UserDAO;
import com.mydiary.my_diary_server.data.dto.UserLoginDTO;
import com.mydiary.my_diary_server.data.entity.User;
import com.mydiary.my_diary_server.data.dto.UserDTO;
import com.mydiary.my_diary_server.data.dto.UserResponseDTO;
import com.mydiary.my_diary_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



import javax.security.sasl.AuthenticationException;



@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO){this.userDAO = userDAO;}


    @Override
    public UserResponseDTO registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        User savedUser = userDAO.insertUser(user);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setEmail(savedUser.getEmail());
        userResponseDTO.setUsername(savedUser.getUsername());

        return userResponseDTO;
    }

    @Override
    public UserResponseDTO loginUser(UserLoginDTO userLoginDTO) {
        String email = userLoginDTO.getEmail();
        String password = userLoginDTO.getPassword();

        // 여기에서 사용자 인증을 수행합니다.
        User authenticatedUser = userDAO.findByEmail(email);

        // 사용자가 존재하고, 패스워드가 일치하는 경우에만 로그인 성공
        if (authenticatedUser != null && authenticatedUser.getPassword().equals(password)) {
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            userResponseDTO.setEmail(authenticatedUser.getEmail());
            userResponseDTO.setUsername(authenticatedUser.getUsername());
            return userResponseDTO;
        } else {
            try {
                throw new AuthenticationException("로그인에 실패하였습니다.");
            } catch (AuthenticationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 아이디 중복 체크
    @Transactional()
    public User checkUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public User joinUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userDAO.insertUser(user);
    }


}
