package com.mydiary.my_diary_server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mydiary.my_diary_server.domain.Likes;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {	
	Likes findByUserIdAndPostId(Long user_id, Long post_id);
}
