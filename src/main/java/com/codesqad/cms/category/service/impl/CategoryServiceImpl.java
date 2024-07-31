package com.codesqad.cms.category.service.impl;

import com.codesqad.cms.category.dto.CategoryDto;
import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.category.entity.Category;
import com.codesqad.cms.exception.ResourceNotFoundException;
import com.codesqad.cms.category.repository.CategoryRepository;
import com.codesqad.cms.category.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private  CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category cat = this.categoryRepository.save(modelMapper.map(categoryDto, Category.class));
        return modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", categoryId)));
        return modelMapper.map(cat, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()
                -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", categoryId)));
        cat.setTitle(categoryDto.getTitle());
        cat.setDescription(categoryDto.getDescription());
        Category savedcategory = this.categoryRepository.save(cat);
        return modelMapper.map(savedcategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        return this.categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDto.class)).toList();
    }

    @Override
    public MessageDto deleteCategory(Long categoryId) {
        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Resource NOT_FOUND", String.format("User with user_id %d not found!", categoryId)));
        this.categoryRepository.delete(cat);
        return new MessageDto("Category deleted successfully");
    }
}
