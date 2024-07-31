package com.codesqad.cms.post.service;

import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.post.dto.PostDto;
import com.codesqad.cms.post.dto.PostResponse;
import com.codesqad.cms.post.entity.Post;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto, Long userId, Long categoryId);

    PostDto updatePost(PostDto postDto, Long id);

    PostDto getSinglePost(Long id) ;

    MessageDto deletePost(Long id);

    List<PostDto> getAllPost();

    PostResponse getAllPostResponse(Integer pageNumber,Integer pageSize, String sortBy, String sortOrder);

    List<PostDto> getPostsByCategory(Long categoryId);

    List<PostDto>  getPostsByUser(Long userId);

    List<PostDto>  getPostsByTag(List<String> tags);

    List<PostDto> searchPost(String Keyword);


    Post savePost(Post post);
}
