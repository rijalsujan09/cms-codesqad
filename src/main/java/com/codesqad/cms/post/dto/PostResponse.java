package com.codesqad.cms.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PostResponse {
    private List<PostDto> postList;
    private long pageNumber;
    private long pageSize;
    private long totalElements;
    private long totalPages;
    private boolean lastPage;
}
