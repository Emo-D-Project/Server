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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "comment")
public class Comment {
	@Id
	private int id;

	private String content;
	private int post_id;
	private long user_id;
}
