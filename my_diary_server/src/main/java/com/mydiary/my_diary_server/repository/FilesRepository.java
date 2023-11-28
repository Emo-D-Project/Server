package com.mydiary.my_diary_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mydiary.my_diary_server.domain.Files;

@Repository
public interface FilesRepository extends JpaRepository<Files, Long>{

}
