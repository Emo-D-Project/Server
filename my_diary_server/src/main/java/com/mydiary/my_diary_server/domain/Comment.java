package com.mydiary.my_diary_server.domain;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String content;
	
	@Column(name = "post_id")
	private Long postId;

	private Long user_id;
	
    @Builder
    public Comment(Long post_id, String content, String author){
    	this.user_id = Long.parseLong(author);
    	this.content = content;
    	this.postId = post_id;
    }
}
