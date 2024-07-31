package com.codesqad.cms.category.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    @NotNull(message = "Category Name is required")
    @Size(min = 3, max = 10, message = "Category Name must be between 3 and 10 characters long")
    private String title;
    private String description;
}
