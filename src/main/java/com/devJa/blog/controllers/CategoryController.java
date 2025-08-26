package com.devJa.blog.controllers;

import com.devJa.blog.Domain.dtos.CategoryDto;
import com.devJa.blog.Domain.entities.Category;
import com.devJa.blog.mappers.CategoryMapper;
import com.devJa.blog.services.CategoryServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryServices  categoryServices;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories() {
        List<CategoryDto> categories = categoryServices.listCategories()
                .stream().map(categoryMapper::toDto)
                .toList();
        return ResponseEntity.ok(categories);
    }
}
