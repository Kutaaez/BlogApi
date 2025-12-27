package kz.seppaku.postmanBlog.mappers;

import kz.seppaku.postmanBlog.dto.response.LikeDto;
import kz.seppaku.postmanBlog.dto.request.LikeCreateDto;
import kz.seppaku.postmanBlog.entities.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username") // или user.actualUsername
    @Mapping(source = "thread.id", target = "postId") // В Entity поле называется 'thread'
    LikeDto toDto(Like like);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "thread", ignore = true)
    Like toEntity(LikeCreateDto dto);

    List<LikeDto> toDtoList(List<Like> likes);
}