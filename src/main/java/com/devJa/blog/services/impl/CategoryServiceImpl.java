package com.devJa.blog.services.impl;

import com.devJa.blog.Domain.entities.Category;
import com.devJa.blog.repositories.CategoryRepository;
import com.devJa.blog.services.CategoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryServices {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
    }
}
