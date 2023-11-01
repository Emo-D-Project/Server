package com.mydiary.my_diary_server.repository;

import com.mydiary.my_diary_server.domain.Diary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>{

    Optional<List<Diary>> findByAuthor(String email);
}
