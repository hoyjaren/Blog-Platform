package com.devJa.blog.services;

import com.devJa.blog.Domain.CreatePostRequest;
import com.devJa.blog.Domain.entities.Post;
import com.devJa.blog.Domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftsPost(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
}
