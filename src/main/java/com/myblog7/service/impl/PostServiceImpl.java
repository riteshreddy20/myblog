package com.myblog7.service.impl;

import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl  implements PostService {

    //post repository isa built in library.

    private PostRepository postRepository;
    // model mapper is a External library.model mapper is not a built in library.
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository,ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        Post savedPost= postRepository.save(post);



        PostDto dto = mapToDto(savedPost);
//        PostDto dto = new PostDto();//update
//         dto.setId(savedPost.getId());
//         dto.setTitle(savedPost.getTitle());
//         dto.setDescription(savedPost.getDescription());
//         dto.setContent(savedPost.getContent());
        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);

    }

    @Override
    public void UpdatePost(long id, PostDto postdto) {
        Post post =postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("post not found  with id:" +id)

        );
        post.setTitle(postdto.getTitle());
        post.setContent(postdto.getContent());
        post.setDescription(postdto.getDescription());

        postRepository.save(post);

    }

    @Override
    public PostDto getPostbyId(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                ()->new ResourceNotFound("post not found with id"+id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    //  public List<PostDto> getposts(int pageNo, int pageSize, String sortBy, String sortDir) {
    public PostResponse getposts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

//        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<Post> pagePosts = postRepository.findAll(pageable);
//        List<Post> posts = pagePosts.getContent();
//        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
//        return postDtos ;
//    }
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> pagePosts = postRepository.findAll(pageable);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        //
        PostResponse postresponse =new PostResponse();
        postresponse.setPostDto(postDtos);
        postresponse.setPageNo(pagePosts.getNumber());
        postresponse.setPageSize(pagePosts.getSize());
        postresponse.setTotalElements(pagePosts.getTotalElements());
        postresponse.setLast(pagePosts.isLast());
        postresponse.setTotalPages(pagePosts.getTotalPages());
        return postresponse ;

    }

    @Override
    public void updatePost(long id, PostDto postDto) {

    }

    @Override
    public PostDto getPostById(Long id) {
        return null;
    }

    @Override
    public PostResponse getPost(int pageNo, int pageSize, String sortBy, String sortOrder) {
        return null;
    }


    //if we convert entity to dto whole code instead of we write only single method (Post mapToDto(Post post){
    // return dto;if in the program reusability happen.we write long code instead we write small two line code.

    PostDto mapToDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//       dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//       dto.setContent(post.getContent());
        return dto;
    }
    //if we convert dto to entity whole code instead of we write only single method (Post mapToEntity(PostDto postdto){
    // return post;if in the program reusability happen.we write long code instead we write small two line code.
    Post mapToEntity(PostDto postdto){
        Post post = modelMapper.map(postdto, Post.class);
//        Post post = new Post();
//       post.setTitle(postdto.getTitle());
//        post.setDescription(postdto.getDescription());
//       post.setContent(postdto.getContent());
        return post;
}
}
