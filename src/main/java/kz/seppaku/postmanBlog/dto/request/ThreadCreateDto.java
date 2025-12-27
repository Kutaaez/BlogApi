package kz.seppaku.postmanBlog.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThreadCreateDto {
    private String title;
    private String text;
    private Long userId;
    private List<Long> categoryIds;
}
