package com.mydiary.my_diary_server.test2;

import com.mydiary.my_diary_server.domain.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
	private long id;
	private final String content;
	private long post_id;
	private long user_id;
	
	public CommentResponse(Comment comment)
	{
		this.id = comment.getId();
		this.content = comment.getContent();
		this.post_id = comment.getPostId();
		this.user_id = comment.getUser_id();
	}
	
}
