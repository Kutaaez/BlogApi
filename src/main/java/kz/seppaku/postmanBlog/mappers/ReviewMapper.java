package kz.seppaku.postmanBlog.mappers;

import kz.seppaku.postmanBlog.dto.response.ReviewDto;
import kz.seppaku.postmanBlog.dto.request.ReviewCreateDto;
import kz.seppaku.postmanBlog.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "thread.id", target = "postId")
    ReviewDto toDto(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "thread", ignore = true)
    Review toEntity(ReviewCreateDto dto);

    List<ReviewDto> toDtoList(List<Review> reviews);
}