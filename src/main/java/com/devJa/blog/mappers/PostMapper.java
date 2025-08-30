package com.devJa.blog.mappers;

import com.devJa.blog.Domain.CreatePostRequest;
import com.devJa.blog.Domain.dtos.CreatePostRequestDto;
import com.devJa.blog.Domain.dtos.PostDto;
import com.devJa.blog.Domain.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {

    @Mapping(target = "author", source = "author")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "tags", source = "tags")
    PostDto toDto(Post post);

    CreatePostRequest toCreatePostRequest(CreatePostRequestDto dto);
}
