package com.codesqad.cms.category.service;

import com.codesqad.cms.category.dto.CategoryDto;
import com.codesqad.cms.dto.MessageDto;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);
    public CategoryDto getCategory(Long categoryId);
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
    public List<CategoryDto> getAllCategory();
    public MessageDto deleteCategory(Long categoryId);
}
