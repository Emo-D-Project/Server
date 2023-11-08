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
	private String content;
	private Long post_id;

	public Comment toEntity(Comment comment)
	{
		return Comment.builder()
				.content(comment.getContent())
				.postId(comment.getPostId())
				.build();
	}
}

