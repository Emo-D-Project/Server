package com.mydiary.my_diary_server.test;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
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
