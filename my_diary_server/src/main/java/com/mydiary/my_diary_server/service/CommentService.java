package com.mydiary.my_diary_server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mydiary.my_diary_server.domain.Comment;
import com.mydiary.my_diary_server.dto.AddCommentRequest;
import com.mydiary.my_diary_server.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {
	private final CommentRepository commentRepository;
	
	public Comment save(AddCommentRequest comment, String author)
	{
		return commentRepository.save(
				new Comment(comment.getPost_id(), comment.getContent(), author)
				);
	}
	
	public List<Comment> find(Long post_id)
	{
		return commentRepository.findByPostId(post_id);
	}
	
	public void delete(long id)
	{
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

      //authorize
        commentRepository.delete(comment);
	}
}
