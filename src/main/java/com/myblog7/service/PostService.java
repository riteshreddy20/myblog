package com.myblog7.service;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;

import java.util.List;


public interface PostService {
    PostDto savePost(PostDto postdto);

    void deletePost(long id);

    void UpdatePost(long id, PostDto postdto);


    PostDto getPostbyId(long id);

    //  public List<PostDto> getposts(int pageNo, int pageSize, String sortBy, String sortDir) {
    PostResponse getposts(int pageNo, int pageSize, String sortBy, String sortDir);

    void updatePost(long id, PostDto postDto);

    //  List<PostDto> getposts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getPostById(Long id);

    PostResponse getPost(int pageNo, int pageSize, String sortBy, String sortOrder);
}
