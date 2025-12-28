package kz.seppaku.postmanBlog.mapper;

import kz.seppaku.postmanBlog.dto.response.UserDto;
import kz.seppaku.postmanBlog.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {ThreadMapper.class, ReviewMapper.class, LikeMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(source = "actualUsername", target = "username")
    @Mapping(source = "threads", target = "posts")
    @Mapping(source = "commentaries", target = "comments")
    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "threads", ignore = true)
    @Mapping(target = "commentaries", ignore = true)
    @Mapping(target = "likes", ignore = true)
    User toEntity(UserDto userDto);
}