package com.devJa.blog.services.impl;

import com.devJa.blog.Domain.CreatePostRequest;
import com.devJa.blog.Domain.PostStatus;
import com.devJa.blog.Domain.UpdatePostRequest;
import com.devJa.blog.Domain.entities.Category;
import com.devJa.blog.Domain.entities.Post;
import com.devJa.blog.Domain.entities.Tag;
import com.devJa.blog.Domain.entities.User;
import com.devJa.blog.repositories.PostRepository;
import com.devJa.blog.services.CategoryServices;
import com.devJa.blog.services.PostService;
import com.devJa.blog.services.TagService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CategoryServices categoryServices;
    private final TagService tagService;

    private static final int WORD_PER_MINUTE = 200;


    @Override
    public Post getPost(UUID id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post does not exist with ID " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts(UUID categoryId, UUID tagId) {
        if (categoryId != null && tagId != null){
            Category category = categoryServices.getCategoryByID(categoryId);
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndCategoryAndTagsContaining(
                    PostStatus.PUBLISHED,
                    category,
                    tag
            );
        }
        if (categoryId != null){
            Category category = categoryServices.getCategoryByID(categoryId);
            return postRepository.findAllByStatusAndCategory(
                    PostStatus.PUBLISHED,
                    category
            );
        }
        if (tagId != null){
            Tag tag = tagService.getTagById(tagId);
            return postRepository.findAllByStatusAndTagsContaining(
                    PostStatus.PUBLISHED,
                    tag
            );
        }
        return postRepository.findAllByStatus(PostStatus.PUBLISHED);
    }

    @Override
    public List<Post> getDraftsPost(User user) {
        return postRepository.findALlByAuthorAndStatus(user, PostStatus.DRAFT);
    }

    @Override
    @Transactional
    public Post createPost(User user, CreatePostRequest createPostRequest) {
        Post newPost = new Post();
        newPost.setTitle(createPostRequest.getTitle());
        newPost.setContent(createPostRequest.getContent());
        newPost.setStatus(createPostRequest.getStatus());
        newPost.setAuthor(user);
        newPost.setReadingTime(calculateReadingTime(createPostRequest.getContent()));

        Category category = categoryServices.getCategoryByID(createPostRequest.getCategoryId());
        newPost.setCategory(category);

        Set<UUID> tagIds = createPostRequest.getTagIds();
        List<Tag> tags = tagService.getTagByIds(tagIds);
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post updatePost(UUID id, UpdatePostRequest updatePostRequest) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Post does not exist with id " + id));

        existingPost.setTitle(updatePostRequest.getTitle());
        String postContent = updatePostRequest.getContent();
        existingPost.setContent(postContent);
        existingPost.setStatus(updatePostRequest.getStatus());
        existingPost.setReadingTime(calculateReadingTime(postContent));

        // Update category only if provided
        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
        UUID existingCategoryId = existingPost.getCategory() != null ? existingPost.getCategory().getId() : null;

        /*
        UUID updatePostRequestCategoryId = updatePostRequest.getCategoryId();
        if (!existingPost.getCategory().getId().equals(updatePostRequestCategoryId)){
            Category newCategory = categoryServices.getCategoryByID(updatePostRequestCategoryId);
            existingPost.setCategory(newCategory);
        }*/

        // Update tags only if provided
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();
        if (updatePostRequestTagIds != null) {
            Set<UUID> existingTagIds = existingPost.getTags().stream()
                    .map(Tag::getId)
                    .collect(Collectors.toSet());

            if (!existingTagIds.equals(updatePostRequestTagIds)) {
                List<Tag> newTags = tagService.getTagByIds(updatePostRequestTagIds);
                existingPost.setTags(new HashSet<>(newTags));
            }
        }

        /*
        Set<UUID> existingTagIds = existingPost.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Set<UUID> updatePostRequestTagIds = updatePostRequest.getTagIds();
        if (!existingTagIds.equals(updatePostRequestTagIds)){
            List<Tag> newTags = tagService.getTagByIds(updatePostRequestTagIds);
            existingPost.setTags(new HashSet<>(newTags));
        }*/
        return postRepository.save(existingPost);
    }

    @Override
    public void deletePost(UUID id) {
        Post post = getPost(id);
        postRepository.delete(post);
    }

    private Integer calculateReadingTime(String content){
        if (content == null || content.isEmpty()){
            return 0;
        }

        int wordCount = content.trim().split("\\s+").length;
        return (int) Math.ceil((double) wordCount/WORD_PER_MINUTE);
    }
}
