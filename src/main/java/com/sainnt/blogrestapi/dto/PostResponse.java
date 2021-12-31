package com.sainnt.blogrestapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> posts;
    private int pageNumber;
    private int pageSize;
    private long totalPosts;
    private int totalPages;
    private boolean last;
}
