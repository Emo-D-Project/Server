package com.mydiary.my_diary_server.test;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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


