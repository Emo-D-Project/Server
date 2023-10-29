package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}

