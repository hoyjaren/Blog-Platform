package com.devJa.blog.mappers;

import com.devJa.blog.Domain.PostStatus;
import com.devJa.blog.Domain.dtos.TagResponse;
import com.devJa.blog.Domain.entities.Post;
import com.devJa.blog.Domain.entities.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    @Mapping(target = "postCount", source = "posts", qualifiedByName = "calculatedPostCount")
    TagResponse toTagResponse(Tag tag);

    @Named("calculatedPostCount")
    default Integer calculatePostCount(Set<Post> posts){
        if (posts == null){
            return 0;
        }
        return (int) posts.stream()
                .filter(post -> PostStatus.PUBLISHED.equals(post.getStatus()))
                .count();
    }


}
