package com.mydiary.my_diary_server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mydiary.my_diary_server.domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}
