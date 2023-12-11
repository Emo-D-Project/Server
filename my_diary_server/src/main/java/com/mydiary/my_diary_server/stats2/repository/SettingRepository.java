package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SettingRepository extends JpaRepository<Setting, Long> {
    Optional<Setting> findByUserId(Long userId);
}

