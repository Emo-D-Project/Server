package com.mydiary.my_diary_server.data.dao.Impl;

import com.mydiary.my_diary_server.data.dao.UserDAO;
import com.mydiary.my_diary_server.data.entity.User;
import com.mydiary.my_diary_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements UserDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserDAOImpl(UserRepository userRepository){this.userRepository = userRepository;}


    @Override
    public User insertUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
