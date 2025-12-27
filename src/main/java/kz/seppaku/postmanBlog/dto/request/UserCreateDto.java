package kz.seppaku.postmanBlog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class UserCreateDto {
    private String username;
    private String email;
    private String password;
    private List<String> roles;
}
