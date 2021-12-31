package com.sainnt.blogrestapi.service.impl;

import com.sainnt.blogrestapi.dto.PostDto;
import com.sainnt.blogrestapi.dto.PostResponse;
import com.sainnt.blogrestapi.entity.Post;
import com.sainnt.blogrestapi.exception.ResourceNotFoundException;
import com.sainnt.blogrestapi.repository.PostRepository;
import com.sainnt.blogrestapi.service.PostService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository repository) {
        this.repository = repository;
    }

    private PostDto mapToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post newPost = repository.save(mapToEntity(postDto));
        return mapToDto(newPost);
    }


    @Override
    public PostResponse getAllPosts(int pageNumber, int pageSize, @NotNull String sortBy,@NotNull String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.DESC.name())?
                Sort.by(sortBy).descending():
                Sort.by(sortBy).ascending();
        final Page<Post> page = repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        PostResponse response = new PostResponse();
        response.setPosts(
                page.stream().map(this::mapToDto).collect(Collectors.toList())
        );
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPosts(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());
        return response;
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(id)));
        return mapToDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, long id) {
        postDto.setId(id);
        Post post = repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(id)));
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatedPost = repository.save(post);
        return mapToDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Post","id",String.valueOf(id)));
        repository.delete(post);
    }
}
