package com.mydiary.my_diary_server.test;

import com.mydiary.my_diary_server.domain.Comment;
import com.mydiary.my_diary_server.dto.AddCommentRequest;
import com.mydiary.my_diary_server.dto.CommentResponse;
import com.mydiary.my_diary_server.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
	private final CommentService commentService;
	
	@PostMapping("/api/comments/create")
    @Operation(summary="댓글 작성")
	public ResponseEntity<Comment> addComment(@RequestBody AddCommentRequest request, Principal principal)
	{
		Comment savedComment  = commentService.save(request, principal.getName());
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(savedComment);
	}
	
    @GetMapping("/api/comments/read/{post_id}")
    @Operation(summary="특정 일기의 댓글확인")
    public ResponseEntity<List<CommentResponse>> findComments(@PathVariable Long post_id) {
        List<CommentResponse> comments = commentService.find(post_id)
                .stream()
                .map(CommentResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(comments);
    }
    
    @DeleteMapping("/api/comments/delete/{id}")
    @Operation(summary="댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable long id) {
        commentService.delete(id);

        return ResponseEntity.ok()
                .build();
    }
}
