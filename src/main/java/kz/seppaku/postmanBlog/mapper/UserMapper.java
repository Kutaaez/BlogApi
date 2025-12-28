package kz.seppaku.postmanBlog.mapper;

import kz.seppaku.postmanBlog.dto.response.UserDto;
import kz.seppaku.postmanBlog.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "actualUsername", target = "username")
    UserDto toDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toEntity(UserDto userDto);
}