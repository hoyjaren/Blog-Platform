package com.devJa.blog.services;

import com.devJa.blog.Domain.CreatePostRequest;
import com.devJa.blog.Domain.UpdatePostRequest;
import com.devJa.blog.Domain.entities.Post;
import com.devJa.blog.Domain.entities.User;

import java.util.List;
import java.util.UUID;

public interface PostService {
    Post getPost(UUID id);
    List<Post> getAllPosts(UUID categoryId, UUID tagId);
    List<Post> getDraftsPost(User user);
    Post createPost(User user, CreatePostRequest createPostRequest);
    Post updatePost(UUID id, UpdatePostRequest updatePostRequest);
    void deletePost(UUID id);
}
