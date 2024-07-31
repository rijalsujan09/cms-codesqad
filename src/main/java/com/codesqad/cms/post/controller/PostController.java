package com.codesqad.cms.post.controller;

import com.codesqad.cms.file.service.FileService;
import com.codesqad.cms.constant.AppConstants;
import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.post.dto.PostDto;
import com.codesqad.cms.post.dto.PostResponse;
import com.codesqad.cms.post.service.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image:/images}")
    private String uploadPath;

    @PostMapping("/create/user/{userId}/category/{categoryId}")
    public ResponseEntity<?> createPost(@PathVariable("userId") Long userId, @PathVariable("categoryId") Long categoryId, @Valid @RequestBody PostDto postDto) throws IOException {

//        String fileName = fileService.uploadImage(uploadPath, file);
        PostDto createdPost = postService.createPost(postDto, userId, categoryId);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long id, @Valid @RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(postDto, id);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<PostDto> getSinglePost(@PathVariable("id") Long id)  {
        PostDto postDto = postService.getSinglePost(id);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageDto> deletePost(@PathVariable("id") Long id) {
        MessageDto messageDto = postService.deletePost(id);
        return new ResponseEntity<>(messageDto, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPost() {
        List<PostDto> postDtoList = postService.getAllPost();
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("resp/list")
    public ResponseEntity<PostResponse> getPostResponse(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {
        PostResponse postResponse = this.postService.getAllPostResponse(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @GetMapping("/get/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getAllPostByCategory(@PathVariable("categoryId") Long categoryId) {
        List<PostDto> postDtoList = postService.getPostsByCategory(categoryId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/get/user/{userId}")
    public ResponseEntity<List<PostDto>> getAllPostByUser(@PathVariable("userId") Long userId) {
        List<PostDto> postDtoList = postService.getPostsByUser(userId);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchPost(@RequestParam(value = "Keyword", defaultValue = "", required = false) String Keyword) {
        List<PostDto> postDtoList = postService.searchPost(Keyword);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @GetMapping("/get/tags")
    public ResponseEntity<?> getPost(@RequestBody List<String> tags) {
        List<PostDto> postDtoList = postService.getPostsByTag(tags);
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @PostMapping("/upload/image/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile file,
                                                   @PathVariable("postId") Long postId) throws IOException {
        PostDto postDto = this.postService.getSinglePost(postId);
        System.out.println(uploadPath);
        String fileName = this.fileService.uploadImage(uploadPath, file);
        postDto.setImageName(fileName);
        this.postService.updatePost(postDto, postId);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping(value = "/get/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(uploadPath, imageName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
    @GetMapping(value = "/fetch/image/{imageName}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> downloadImage1(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
        InputStream resource = this.fileService.getResource(uploadPath, imageName);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        return ResponseEntity.status(HttpStatus.OK).body(StreamUtils.copyToByteArray(resource));
    }


}
