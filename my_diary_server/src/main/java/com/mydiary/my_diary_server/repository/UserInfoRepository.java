package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<List<UserInfo>> findAllByUserId(Long userId);

}
