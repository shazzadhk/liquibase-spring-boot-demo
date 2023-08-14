package com.shazzad.auditdemo.service;

import com.shazzad.auditdemo.entity.Post;
import com.shazzad.auditdemo.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class PostService {

    private final WebClient webClient;
    private final PostRepository postRepository;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    public List<Post> getAllPosts(){
        return webClient
            .get()
            .uri("posts")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<Post>>() {})
            .block();
    }

    public List<Post> saveAllPosts(){
        List<Post> postList = getAllPosts();
        int totalObjects = postList.size();
        for (int i = 0; i < totalObjects; i = i + batchSize) {
            if (i + batchSize > totalObjects) {
                List<Post> posts = postList.subList(i, totalObjects - 1);
                postRepository.saveAll(posts);
                break;
            }
            List<Post> posts = postList.subList(i, i + batchSize);
            postRepository.saveAll(posts);
        }
//        postRepository.saveAll(postList);
        return postList;
    }
}
