package kz.seppaku.postmanBlog.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewCreateDto {
    private String text;
    private Long threadId;
}
