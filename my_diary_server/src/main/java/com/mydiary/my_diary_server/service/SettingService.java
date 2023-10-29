package com.mydiary.my_diary_server.service;

import com.mydiary.my_diary_server.domain.Setting;
import com.mydiary.my_diary_server.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingService {

    private final SettingRepository settingRepository;

    @Autowired
    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    // 사용자 설정 생성
    public Setting createSetting(Setting setting) {
        return settingRepository.save(setting);
    }

    // 사용자 설정 조회 by ID
    public Setting getSettingById(Long id) {
        Optional<Setting> settingOptional = settingRepository.findById(id);
        return settingOptional.orElse(null);
    }

    // 사용자 설정 업데이트 by ID
    public Setting updateSetting(Long id, Setting updatedSetting) {
        Optional<Setting> settingOptional = settingRepository.findById(id);
        if (settingOptional.isPresent()) {
            Setting setting = settingOptional.get();
            setting.setAllowMsg(updatedSetting.isAllowMsg());
            setting.setMsgAlarm(updatedSetting.isMsgAlarm());
            setting.setEmpAlarm(updatedSetting.isEmpAlarm());
            setting.setCommAlarm(updatedSetting.isCommAlarm());
            setting.setActAlarm(updatedSetting.isActAlarm());

            return settingRepository.save(setting);
        }
        return null;
    }

    // 사용자 설정 삭제 by ID
    public void deleteSetting(Long id) {
        settingRepository.deleteById(id);
    }
}

