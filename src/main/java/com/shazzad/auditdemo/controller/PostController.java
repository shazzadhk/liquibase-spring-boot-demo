package com.shazzad.auditdemo.controller;

import com.shazzad.auditdemo.entity.Post;
import com.shazzad.auditdemo.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/getPosts")
    public List<Post> savePost(){
        return postService.saveAllPosts();
    }
}
