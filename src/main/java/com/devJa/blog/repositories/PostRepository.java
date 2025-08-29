package com.devJa.blog.repositories;

import com.devJa.blog.Domain.PostStatus;
import com.devJa.blog.Domain.entities.Category;
import com.devJa.blog.Domain.entities.Post;
import com.devJa.blog.Domain.entities.Tag;
import com.devJa.blog.services.PostService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByStatusAndCategoryAndTagsContaining(PostStatus status, Category category, Tag tag);
    List<Post> findAllByStatusAndCategory(PostStatus status, Category category);
    List<Post> findAllByStatusAndTagsContaining(PostStatus status, Tag tag);
    List<Post> findAllByStatus(PostStatus status);
}
