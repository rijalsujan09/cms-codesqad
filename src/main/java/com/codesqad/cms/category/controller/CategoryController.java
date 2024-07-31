package com.codesqad.cms.category.controller;

import com.codesqad.cms.category.dto.CategoryDto;
import com.codesqad.cms.dto.MessageDto;
import com.codesqad.cms.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = this.categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Long categoryId) {
        CategoryDto categoryDto = this.categoryService.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Long categoryId, @Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = this.categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<MessageDto> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        MessageDto Message = this.categoryService.deleteCategory(categoryId);
        return new ResponseEntity<MessageDto>(Message, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAllCategory() {
        List<CategoryDto> categoryList = this.categoryService.getAllCategory();
        return new ResponseEntity<List<CategoryDto>>(categoryList, HttpStatus.OK);
    }
}
