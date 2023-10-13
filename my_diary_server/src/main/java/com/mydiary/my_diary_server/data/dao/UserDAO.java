package com.mydiary.my_diary_server.data.dao;

import com.mydiary.my_diary_server.data.entity.User;

public interface UserDAO {
    User insertUser(User user);

    User findByEmail(String username);

    User findByUsername(String username);
}
