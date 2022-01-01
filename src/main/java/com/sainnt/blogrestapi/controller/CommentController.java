package com.sainnt.blogrestapi.controller;

import com.sainnt.blogrestapi.dto.CommentDto;
import com.sainnt.blogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "posts/{postId}/comments")
public class CommentController {
    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") long postId, @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId( @PathVariable(name = "postId") long postId){
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping(path = "/{commentId}")
    public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable(name = "postId") long postId,
                                                            @PathVariable(name="commentId") long commentId
    ){
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @PutMapping(path = "/{commentId}")
    public ResponseEntity<CommentDto> updateComment( @PathVariable(name = "postId") long postId,
                                                     @PathVariable(name="commentId") long commentId,
                                                     @RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") long postId,
                                                @PathVariable(name="commentId") long commentId
    ){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment successfully deleted");
    }

}
