package com.sainnt.blogrestapi.repository;

import com.sainnt.blogrestapi.entity.Post;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PostRepository extends PagingAndSortingRepository<Post,Long> {
}
