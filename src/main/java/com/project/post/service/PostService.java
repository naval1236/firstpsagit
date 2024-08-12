package com.project.post.service;

import com.project.post.config.RestTemplateConfig;
import com.project.post.entity.Post;
import com.project.post.payload.PostDto;
import com.project.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private RestTemplateConfig restTemplateConfig;

    public Post createPost(Post post){
        String postId = UUID.randomUUID().toString();
        post.setId(postId);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }
    public Post getPost(String postId){
        Post post = postRepository.findById(postId).get();
        return post;
    }
    public PostDto getPostWithComments(String postId){
        Post post = postRepository.findById(postId).get();
        ArrayList comments = restTemplateConfig.getRestTemplate().getForObject("http://COMMENT-SERVICE/api/comments/" + postId, ArrayList.class);
        PostDto postDto=new PostDto();
        postDto.setPostId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setComments(comments);
        return postDto;
    }
}
