package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingRepository extends JpaRepository<Setting, Long> {
}

