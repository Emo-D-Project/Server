package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.data.entity.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>{

}
