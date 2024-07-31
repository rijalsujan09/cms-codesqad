package com.codesqad.cms.post.controller;
import com.codesqad.cms.post.dto.PostDto;
import com.codesqad.cms.post.entity.Post;
import com.codesqad.cms.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/post")
public class TestController {

    @Autowired
    private PostService postService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/create")
    public Post createPost(@RequestBody PostDto postJson) throws IOException {
        Post post = new Post();
        post.setTitle(postJson.getTitle());
        post.setContent(postJson.getContent());
        post.setTags(postJson.getTags());
        return postService.savePost(post);
    }


    // Additional endpoints
}