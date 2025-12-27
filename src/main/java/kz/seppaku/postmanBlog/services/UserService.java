package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.UserCreateDto;
import kz.seppaku.postmanBlog.dto.response.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService  extends UserDetailsService  {
    List<UserDto> getAll();
    UserDto getById(Long id);
    UserDto getByEmail(String email);
    UserDto create(UserCreateDto userCreateDto);
    UserDto update(Long id, UserCreateDto userCreateDto);
    boolean delete(Long id);

}
