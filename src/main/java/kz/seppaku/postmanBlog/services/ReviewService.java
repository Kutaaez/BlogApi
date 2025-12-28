package kz.seppaku.postmanBlog.services;
import kz.seppaku.postmanBlog.dto.response.ReviewDto;
import kz.seppaku.postmanBlog.dto.request.ReviewCreateDto;

import java.util.List;
public interface ReviewService {
    List<ReviewDto> getAll();
    ReviewDto getById(Long id);
    ReviewDto create(ReviewCreateDto reviewCreateDto);
    ReviewDto update(Long id, ReviewDto reviewDto);
    boolean delete(Long id);
}
