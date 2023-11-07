package com.mydiary.my_diary_server.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mydiary.my_diary_server.domain.Comment;
import com.mydiary.my_diary_server.dto.AddCommentRequest;
import com.mydiary.my_diary_server.dto.CommentResponse;
import com.mydiary.my_diary_server.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
	private final CommentService commentService;
	
	@PostMapping("/api/comments")
	public ResponseEntity<Comment> addComment(@RequestBody AddCommentRequest request)
	{
		Comment savedComment  = commentService.save(
				new Comment(request.getUser_id(), request.getPost_id(), request.getContent()));
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedComment);
	}
	
    @GetMapping("/api/comments/{post_id}")
    public ResponseEntity<List<CommentResponse>> findComments(@PathVariable Integer post_id) {
        List<CommentResponse> comments = commentService.find(post_id)
                .stream()
                .map(CommentResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(comments);
    }
    
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        commentService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
