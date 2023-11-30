package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.UserInfo;
import com.mydiary.my_diary_server.dto.UserInfoResponse;
import com.mydiary.my_diary_server.repository.UserInfoRepository;
import com.mydiary.my_diary_server.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserInfoService {

    UserInfoRepository userInfoRepository;
    UserRepository userRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository, UserRepository userRepository){
        this.userInfoRepository = userInfoRepository;
        this.userRepository = userRepository;
    }


    public List<UserInfoResponse> findAllById(Long id){
        List<UserInfo> userInfos = userInfoRepository.findAllByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));

        List<UserInfoResponse> userInfoResponses = new ArrayList<>();
        for (UserInfo userInfo : userInfos) {
            userInfoResponses.add(new UserInfoResponse(userInfo));
        }
        return userInfoResponses;
    }

    @Transactional
    public UserInfoResponse saveOrUpdate(UserInfo userInfo, Long userId) {
        /*List<UserInfo> userInfos = userInfoRepository.findAllByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + userId));
        boolean check = false;
        for (UserInfo userInfo1 : userInfos) {
            if(userInfo.equals(userInfo1)){
                check = true;
            }
        }

        if(check){
            UserInfo userInfo1 = userInfoRepository.findByUserId(userId).get();
            userInfo1.update(userInfo);

            return new UserInfoResponse(userInfo1);
        }
        if (userRepository.findById(userId).isPresent()) {
            userInfo.setUser(userRepository.findById(userId).get());
        }*/

        return new UserInfoResponse(userInfoRepository.save(userInfo));
    }
}
