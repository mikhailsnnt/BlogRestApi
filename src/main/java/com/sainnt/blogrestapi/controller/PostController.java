package com.sainnt.blogrestapi.controller;

import com.sainnt.blogrestapi.dto.PostDto;
import com.sainnt.blogrestapi.dto.PostResponse;
import com.sainnt.blogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity <PostResponse> getAllPosts(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc",required = false) String sortDir
    ){
        return ResponseEntity.ok( postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.updatePost(postDto,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name="id") long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }

}
