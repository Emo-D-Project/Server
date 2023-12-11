package com.mydiary.my_diary_server.domain;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "likes")
 public class Likes {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
      
	
	@Column(name="post_id")
	private Long postId;
	
	@Column(name="user_id")
	private Long userId;
	
	@Builder
	public Likes(Long post_id, Long user_id)
	{
		this.postId = post_id;
		this.userId = user_id;
	}
}


