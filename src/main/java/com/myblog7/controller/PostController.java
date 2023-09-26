package com.myblog7.controller;

import com.myblog7.payload.PostDto;
import com.myblog7.payload.PostResponse;
import com.myblog7.service.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

  public PostController(PostService postService) {
      this.postService = postService;
   }

    @PreAuthorize("hasRole('ADMIN')")
    //http://localhost:8080/api/post
    @PostMapping
   // public ResponseEntity<PostDto> savePost(@Valid @RequestBody PostDto postDto, BindingResult result) {
        public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result) {
      if(result.hasErrors()){

          return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
      }
        PostDto dto = postService.savePost(postDto);
        return  new ResponseEntity<>(dto, HttpStatus.CREATED);//201
    }
    @PreAuthorize("hasRole('ADMIN')")
    //http://localhost:8080/api/post/19
    @DeleteMapping("/{id}")
 public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted", HttpStatus.OK);//200
 }
    @PreAuthorize("hasRole('ADMIN')")
 @PutMapping("/{id}")
 public ResponseEntity<PostDto>updatePost(@PathVariable("id") long id,@RequestBody PostDto Postdto) {
      postService.UpdatePost(id, Postdto);
return null;
     //return new ResponseEntity<>("post is updated", HttpStatus.OK);//200
 }
 @GetMapping("/{id}")
 public ResponseEntity<PostDto> getPostbyId(@PathVariable("id") long id){
     PostDto dto = postService.getPostbyId(id);
     return new ResponseEntity<>(dto,HttpStatus.OK);//200
 }
 //Pagination
 //http://localhost:8080/api/post?pageNo=1&pageSize=5&sortBy=title&sortDir=desc
// @GetMapping
// public List<PostDto> getposts(@RequestParam(value="pageNo",defaultValue = "0",required=false)int pageNo,
//                               @RequestParam(value="pageSize",defaultValue ="3",required=false)int pageSize,
//                               @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
//                               @RequestParam(value = "sortDir",defaultValue ="asc",required = false)String sortDir
// ){


    @GetMapping
     public PostResponse getposts(@RequestParam(value="pageNo",defaultValue = "0",required=false)int pageNo,
     @RequestParam(value="pageSize",defaultValue ="3",required=false)int pageSize,
     @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
     @RequestParam(value = "sortDir",defaultValue ="asc",required = false)String sortDir
 ){
    // List<PostDto> postDtostos = postService.getposts(pageNo,pageSize,sortBy,sortDir);
     PostResponse postresponse = postService.getposts(pageNo,pageSize,sortBy,sortDir);
     return postresponse ;
 }
}
