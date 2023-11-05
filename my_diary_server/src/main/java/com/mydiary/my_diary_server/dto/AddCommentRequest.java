package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Comment;
import com.mydiary.my_diary_server.domain.Diary;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
	private long user_id;
	private String content;
	private int post_id;

	public Comment toEntity(Comment comment)
	{
		return Comment.builder()
				.user_id(comment.getUser_id())
				.content(comment.getContent())
				.post_id(comment.getPostId())
				.build();
	}
}

