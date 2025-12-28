package kz.seppaku.postmanBlog.mapper;

import kz.seppaku.postmanBlog.dto.response.LikeDto;
import kz.seppaku.postmanBlog.entities.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LikeMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.actualUsername", target = "username")
    @Mapping(source = "thread.id", target = "threadId")
    LikeDto toDto(Like like);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "thread", ignore = true)
    Like toEntity(LikeDto likeDto);
}