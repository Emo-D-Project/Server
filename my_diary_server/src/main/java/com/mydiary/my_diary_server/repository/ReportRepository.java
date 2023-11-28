package com.mydiary.my_diary_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mydiary.my_diary_server.domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long>{
	List<Report> findByUserId(Long user_id);
}
