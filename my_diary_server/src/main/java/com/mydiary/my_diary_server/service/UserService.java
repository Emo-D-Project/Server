package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public Long save(AddUserRequest dto){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(encoder.encode(dto.getPassword()))
                .build()).getId();
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Unexpected user"));
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("unexpected user"))''
    }


//    public UserResponseDTO registerUser(UserDTO userDTO) {
//        User user = new User();
//        user.setUsername(userDTO.getUsername());
//
//        user.setPassword(userDTO.getPassword());
//        user.setEmail(userDTO.getEmail());
//
//        User savedUser = userDAO.insertUser(user);
//
//        UserResponseDTO userResponseDTO = new UserResponseDTO();
//        userResponseDTO.setEmail(savedUser.getEmail());
//        userResponseDTO.setUsername(savedUser.getUsername());
//
//        return userResponseDTO;
//    }

//    public UserResponseDTO loginUser(UserLoginDTO userLoginDTO) {
//        String email = userLoginDTO.getEmail();
//        String password = userLoginDTO.getPassword();
//
//        // 여기에서 사용자 인증을 수행합니다.
//        User authenticatedUser = userDAO.findByEmail(email);
//
//        // 사용자가 존재하고, 패스워드가 일치하는 경우에만 로그인 성공
//        if (authenticatedUser != null && authenticatedUser.getPassword().equals(password)) {
//            UserResponseDTO userResponseDTO = new UserResponseDTO();
//            userResponseDTO.setEmail(authenticatedUser.getEmail());
//            userResponseDTO.setUsername(authenticatedUser.getUsername());
//            return userResponseDTO;
//        } else {
//            try {
//                throw new AuthenticationException("로그인에 실패하였습니다.");
//            } catch (AuthenticationException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }

    // 아이디 중복 체크
    @Transactional()
    public User checkUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User joinUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


}
