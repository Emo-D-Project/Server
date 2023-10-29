package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.data.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
}

