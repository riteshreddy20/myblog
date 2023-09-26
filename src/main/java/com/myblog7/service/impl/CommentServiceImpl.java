package com.myblog7.service.impl;

import com.myblog7.entity.Comment;
import com.myblog7.entity.Post;
import com.myblog7.exception.ResourceNotFound;
import com.myblog7.payload.CommentDto;
import com.myblog7.repository.CommentRepository;
import com.myblog7.repository.PostRepository;
import com.myblog7.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public CommentServiceImpl(ModelMapper modelMapper,PostRepository postRepository,CommentRepository commentRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;


    }




    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment= mapDtoToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id" + postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto =mapEntityToDto(savedComment);
        return dto;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id" + postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id" + postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id" + postId)
        );
        CommentDto commentDto = mapEntityToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> comments = commentRepository.findAll();
        comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());
        return comments.stream().map(comment -> mapEntityToDto(comment)).collect(Collectors.toList());

    }

    @Override
    public void deleteCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("post not found with id" + postId)
        );

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment not found with id" + postId)
        );
        commentRepository.deleteById(commentId);
    }

    private CommentDto mapEntityToDto(Comment savedComment) {
        CommentDto dto = modelMapper.map(savedComment, CommentDto.class);
        return dto;
    }

    private Comment mapDtoToEntity(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
}
}
