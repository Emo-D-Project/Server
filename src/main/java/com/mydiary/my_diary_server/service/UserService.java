package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.dto.AddUserRequest;
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
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("unexpected user"));
    }

    // 아이디 중복 체크
    @Transactional()
    public User checkUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User joinUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


    public String getDiaryPassword(Long userId){
        return userRepository.findById(userId).get().getDiaryPassword();

    }

    public String changeDiaryPassword(long userId, String password) {
        User user = userRepository.findById(userId).get();
        user.setDiaryPassword(password);
        userRepository.save(user);
        return "비밀번호 변경 완료";
    }

    public String switchDiaryPassword(long userId) {
        User user = userRepository.findById(userId).get();
        user.setDiaryPasswordSwitch(!user.isDiaryPasswordSwitch());
        userRepository.save(user);
        return "비밀번호 on/off 설정 변경 완료";
    }

    public String checkDiaryPasswordSwitch(long l) {
User user = userRepository.findById(l).get();
        return user.isDiaryPasswordSwitch() ? "true" : "false";
    }
}
