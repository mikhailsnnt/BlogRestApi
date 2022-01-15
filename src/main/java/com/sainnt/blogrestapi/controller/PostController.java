package com.sainnt.blogrestapi.controller;

import com.sainnt.blogrestapi.dto.PostDto;
import com.sainnt.blogrestapi.dto.PostResponse;
import com.sainnt.blogrestapi.service.PostService;
import com.sainnt.blogrestapi.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR,required = false) String sortDir
    ){
        return ResponseEntity.ok( postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

}
