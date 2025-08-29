package com.devJa.blog.services;

import com.devJa.blog.Domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryServices {
    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(UUID id);
    Category getCategoryByID(UUID id);
}
