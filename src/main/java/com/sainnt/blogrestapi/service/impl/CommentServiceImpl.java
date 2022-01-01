package com.sainnt.blogrestapi.service.impl;

import com.sainnt.blogrestapi.dto.CommentDto;
import com.sainnt.blogrestapi.entity.Comment;
import com.sainnt.blogrestapi.entity.Post;
import com.sainnt.blogrestapi.exception.BlogApiException;
import com.sainnt.blogrestapi.exception.ResourceNotFoundException;
import com.sainnt.blogrestapi.repository.CommentRepository;
import com.sainnt.blogrestapi.repository.PostRepository;
import com.sainnt.blogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        return mapToDto(commentRepository.save(comment));


    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        return post.getComments().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        return mapToDto(getCommentWithinPost(postId,commentId));
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Comment comment = getCommentWithinPost(postId, commentId);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return mapToDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        commentRepository.delete(getCommentWithinPost(postId,commentId));
    }

    private CommentDto mapToDto(Comment comment){
        return new CommentDto(comment.getId(), comment.getName(), comment.getEmail(), comment.getBody());
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    private Comment getCommentWithinPost(long postId, long commentId){
        Post post = postRepository.findById(postId)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(postId)));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new ResourceNotFoundException("Comment","id",String.valueOf(commentId)));
        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogApiException(String.format("Comment id [%d] does not belong to post id [%d]",commentId,postId));
        return comment;
    }

}
