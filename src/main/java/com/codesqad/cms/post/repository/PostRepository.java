package com.codesqad.cms.post.repository;

import com.codesqad.cms.category.entity.Category;
import com.codesqad.cms.post.entity.Post;
import com.codesqad.cms.account.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category category);
    List<Post> findByUser(UserAccount user);
    List<Post> findByTitleContaining(String keyword);
}
