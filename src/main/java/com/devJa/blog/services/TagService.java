package com.devJa.blog.services;

import com.devJa.blog.Domain.entities.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {

    List<Tag> getTags();
    List<Tag> createTags(Set<String> tagNames);
}
