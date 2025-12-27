package kz.seppaku.postmanBlog.mappers;

import kz.seppaku.postmanBlog.dto.response.UserDto;
import kz.seppaku.postmanBlog.dto.request.UserCreateDto;
import kz.seppaku.postmanBlog.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ThreadMapper.class, ReviewMapper.class, LikeMapper.class})
public interface UserMapper {

    @Mapping(source = "username", target = "username")
    @Mapping(source = "threads", target = "posts")
    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "threads", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "likes", ignore = true)
    User toEntity(UserCreateDto dto);

    List<UserDto> toDtoList(List<User> users);
}