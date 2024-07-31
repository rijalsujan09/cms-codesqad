package com.codesqad.cms.post.service.impl;

import com.codesqad.cms.category.dto.CategoryDto;
import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.post.dto.PostDto;
import com.codesqad.cms.account.dto.UserDtoSave;
import com.codesqad.cms.category.entity.Category;
import com.codesqad.cms.post.dto.PostResponse;
import com.codesqad.cms.post.entity.Post;
import com.codesqad.cms.account.entity.UserAccount;
import com.codesqad.cms.exception.ResourceNotFoundException;
import com.codesqad.cms.category.repository.CategoryRepository;
import com.codesqad.cms.post.repository.PostRepository;
import com.codesqad.cms.account.repository.UserRepository;
import com.codesqad.cms.post.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Long userId, Long categoryId) {
        UserAccount user = this.userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", userId)));
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", categoryId)));
        Post post = this.modelMapper.map(postDto, Post.class);
        post.setImageName("default.png");
        post.setUser(user);
        post.setCategory(category);
        Post newPost = this.postRepository.save(post);

        PostDto newPostDto = this.modelMapper.map(newPost, PostDto.class);
//        newPostDto.setUser(modelMapper.map(user, UserDtoSave.class));
//        newPostDto.setCategory(modelMapper.map(category, CategoryDto.class));
        return newPostDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", id)));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
        Post updatedPost = this.postRepository.save(post);
        return this.modelMapper.map(updatedPost, PostDto.class);
    }

    @Override
    public PostDto getSinglePost(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", id)));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public MessageDto deletePost(Long id) {
        if (!this.postRepository.existsById(id)) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", id));
        } else {
            this.postRepository.deleteById(id);
            return new MessageDto("Post deleted successfully");
        }
    }

    @Override
    public List<PostDto> getAllPost() {
        List<Post> postList = this.postRepository.findAll();
        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public PostResponse getAllPostResponse(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if (!sortOrder.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.DESC;
        }
        Sort sort = Sort.by(direction, sortBy);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Post> pagePost = this.postRepository.findAll(pageRequest);
        List<Post> postList = pagePost.getContent();

        List<PostDto> postDtoList = postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();

        PostResponse postResponse = new PostResponse();
        postResponse.setPostList(postDtoList);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }


    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", categoryId)));
        List<Post> postList = this.postRepository.findByCategory(category);
        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        UserAccount user = this.userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", userId)));
        List<Post> postList = this.postRepository.findByUser(user);
        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public List<PostDto> getPostsByTag(List<String> tags) {
        List<Post> postList = this.postRepository.findAll();
        return postList.stream().filter(post -> post.getTags().stream().anyMatch(tags::contains)).map(post -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public List<PostDto> searchPost(String Keyword) {
        List<Post> postList = this.postRepository.findByTitleContaining(Keyword);
        return postList.stream().map(post -> this.modelMapper.map(post, PostDto.class)).toList();
    }

    @Override
    public Post savePost(Post post) {
        System.out.println("saving post");
        return null;
    }

}
