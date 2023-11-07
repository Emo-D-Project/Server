package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.UserInfo;
import com.mydiary.my_diary_server.dto.UserInfoResponse;
import com.mydiary.my_diary_server.repository.UserInfoRepository;
import com.mydiary.my_diary_server.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {

    UserInfoRepository userInfoRepository;
    UserRepository userRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository, UserRepository userRepository){
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
    }


    public UserInfoResponse findById(Long id){
        return new UserInfoResponse(userInfoRepository.findByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id)));
    }

    @Transactional
    public UserInfoResponse saveOrUpdate(UserInfo userInfo, Long userId) {
        if(userInfoRepository.findByUserId(userId).isPresent()){
            UserInfo userInfo1 = userInfoRepository.findByUserId(userId).get();
            userInfo1.update(userInfo);

            return new UserInfoResponse(userInfo1);
        }
        if (userRepository.findById(userId).isPresent()) {
            userInfo.setUser(userRepository.findById(userId).get());
        }

        return new UserInfoResponse(userInfoRepository.save(userInfo));
    }
}
