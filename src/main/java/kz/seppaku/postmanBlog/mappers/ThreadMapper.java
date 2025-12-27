package kz.seppaku.postmanBlog.mappers;

import kz.seppaku.postmanBlog.dto.response.ThreadDto;
import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.entities.Thread;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class, CategoryMapper.class, LikeMapper.class})
public interface ThreadMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "reviews", target = "comments")
    ThreadDto toDto(Thread thread);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Thread toEntity(ThreadCreateDto dto);

    List<ThreadDto> toDtoList(List<Thread> threads);
}