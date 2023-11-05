package com.mydiary.my_diary_server.dto;

import com.mydiary.my_diary_server.domain.Comment;

import lombok.Getter;

@Getter
public class CommentResponse {
	private final String content;
	private int post_id;
	private long user_id;
	
	public CommentResponse(Comment comment)
	{
		this.content = comment.getContent();
		this.post_id = comment.getPostId();
		this.user_id = comment.getUser_id();
	}
	
}
