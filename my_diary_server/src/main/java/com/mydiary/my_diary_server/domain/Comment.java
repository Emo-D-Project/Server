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
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String content;
	
	@Column(name = "post_id")
	private long postId;
	
	private long user_id;
	
    @Builder
    public Comment(long user_id, int post_id, String content){
    	this.user_id = user_id;
    	this.content = content;
    	this.postId = post_id;
    }
}
