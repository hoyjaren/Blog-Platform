package com.devJa.blog.mappers;

import com.devJa.blog.Domain.PostStatus;
import com.devJa.blog.Domain.dtos.CategoryDto;
import com.devJa.blog.Domain.entities.Category;
import com.devJa.blog.Domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatePostCount")
    CategoryDto toDto (Category category);

    @Named("calculatePostCount")
    default long calculatePostCount(List<Post> posts){
        if(null == posts){
            return 0;
        }
        return posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }
}
