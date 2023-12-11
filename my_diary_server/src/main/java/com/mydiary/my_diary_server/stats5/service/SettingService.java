package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Setting;
import com.mydiary.my_diary_server.repository.SettingRepository;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class SettingService {
    private final SettingRepository settingRepository;
    @Autowired
    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    // 사용자 설정 조회 by ID
    @Operation (summary = "유저 설정 불러오는 기능")
    public Setting findSettingByUserId(Long userId) {
        Optional<Setting> settingOptional = settingRepository.findByUserId(userId);
        return settingOptional.orElse(null);
    }
    @Operation(summary = "설정 정보 저장")
    @Transactional
    public Setting createOrUpdate(Setting newSetting, Long userId) {
        Optional<Setting> settingOptional = settingRepository.findByUserId(userId);
        if (settingOptional.isPresent()) { // 기존 세팅이 존재할 경우
            Setting setting = settingOptional.get();
            setting.setAllowMsg(newSetting.isAllowMsg());
            setting.setMsgAlarm(newSetting.isMsgAlarm());
            setting.setEmpAlarm(newSetting.isEmpAlarm());
            setting.setCommAlarm(newSetting.isCommAlarm());
            setting.setActAlarm(newSetting.isActAlarm());
            return settingRepository.save(setting);
        } else { // 기존 세팅이 존재하지 않을 경우
            return settingRepository.save(newSetting);
        }
    }


}

