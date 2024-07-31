package com.codesqad.cms.post.dto;

import com.codesqad.cms.account.dto.UserDtoSave;
import com.codesqad.cms.category.dto.CategoryDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String title;
    private String content;
//    @NotNull(message = "category is required")
    private List<String> tags;
    private String imageName;
//    private Date createdDate;
//    private UserDtoSave user;
//    private CategoryDto category;

}
