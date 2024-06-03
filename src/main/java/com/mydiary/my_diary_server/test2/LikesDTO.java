package com.mydiary.my_diary_server.test2;

import com.mydiary.my_diary_server.domain.Likes;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LikesDTO {
	private Long postId;
	private Long userId;
	
	public LikesDTO(long post_id, long user_id)
	{
		this.postId = post_id;
		this.userId = user_id;
	}
	
	public Likes toEntity(Likes like)
	{
		return Likes.builder()
				.postId(like.getPostId())
				.userId(like.getUserId())
				.build();
	}
}

