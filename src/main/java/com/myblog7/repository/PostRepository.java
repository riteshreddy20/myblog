package com.myblog7.repository;


import com.myblog7.entity.Post;
import com.myblog7.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository  extends JpaRepository<Post,Long> {


 // List<CommentDto> findByPostId(long postId);
}
