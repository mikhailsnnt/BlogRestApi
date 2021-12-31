package com.sainnt.blogrestapi.service;

import com.sainnt.blogrestapi.dto.PostDto;
import com.sainnt.blogrestapi.dto.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNumber, int pageSize, String sortBy,String sortDir);
    PostDto getPostById(long id);
    PostDto updatePost(PostDto postDto,long id);
    void deletePost(long id);
}
