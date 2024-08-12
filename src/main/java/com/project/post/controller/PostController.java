package com.project.post.controller;

import com.project.post.entity.Post;
import com.project.post.payload.PostDto;
import com.project.post.service.PostService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post post1 = postService.createPost(post);
        return new ResponseEntity<>(post1, HttpStatus.CREATED);
    }

    //http://localhost:6060/api/posts/{postId}
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable String postId){
        Post post = postService.getPost(postId);
        return new ResponseEntity<>(post,HttpStatus.OK);
    }
    @GetMapping("/{postId}/comments")
    @CircuitBreaker(name = "commentBreaker", fallbackMethod = "commentFallback")

    public ResponseEntity<PostDto> getPostWithComments(@PathVariable String postId){
        PostDto postDto = postService.getPostWithComments(postId);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

    public ResponseEntity<PostDto> commentFallback(String postId, Exception ex){
        System.out.println("Fallback is executed because service is down :" + ex.getMessage());
        ex.printStackTrace();

        PostDto dto=new PostDto();
        dto.setPostId("1234");
        dto.setTitle("Service Down");
        dto.setDescription("Service Down");
        dto.setContent("Service Down");
        return new ResponseEntity<>(dto,HttpStatus.BAD_REQUEST);
    }

}
