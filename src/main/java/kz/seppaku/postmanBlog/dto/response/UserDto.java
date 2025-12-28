package kz.seppaku.postmanBlog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private boolean banned;
    private List<ThreadDto> posts;
    private List<ReviewDto> comments;
    private List<LikeDto> likes;
}