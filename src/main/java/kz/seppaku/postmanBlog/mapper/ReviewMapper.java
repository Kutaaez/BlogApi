package kz.seppaku.postmanBlog.mapper;

import kz.seppaku.postmanBlog.dto.response.ReviewDto;
import kz.seppaku.postmanBlog.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.actualUsername", target = "username")
    @Mapping(source = "thread.id", target = "postId")
    ReviewDto toDto(Review review);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "thread", ignore = true)
    Review toEntity(ReviewDto reviewDto);
}