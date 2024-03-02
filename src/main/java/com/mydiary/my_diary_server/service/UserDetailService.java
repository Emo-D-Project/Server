package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.User;
import com.mydiary.my_diary_server.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) {

        return userRepository.findByUsername(email);
    }
}
