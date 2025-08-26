package com.devJa.blog.repositories;

import com.devJa.blog.Domain.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}
