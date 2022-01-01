package com.sainnt.blogrestapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "comments")
public class PostDto {
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Title  should contain at least 2 characters")
    private String title;
    @NotEmpty
    @Size(min = 2, message = "Description should contain at least 2 characters")
    private String description;
    @NotEmpty
    @Size(min = 10, message = "Post body  should contain at least 2 characters")
    private String content;
    private Set<CommentDto> comments;
}
