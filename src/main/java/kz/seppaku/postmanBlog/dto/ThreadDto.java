package kz.seppaku.postmanBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadDto {
    private Long id;
    private String title;
    private String text;
    private Long userId;
    private String username;
    private List<ReviewDto> comments;
    private List<CategoryDto> categories;
    private List<LikeDto> likes;
}
