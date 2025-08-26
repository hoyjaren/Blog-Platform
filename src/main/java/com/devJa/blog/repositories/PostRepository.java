package com.devJa.blog.repositories;

import com.devJa.blog.Domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

}
