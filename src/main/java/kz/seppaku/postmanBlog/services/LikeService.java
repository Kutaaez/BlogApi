package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.LikeCreateDto;
import kz.seppaku.postmanBlog.dto.response.LikeDto;

import java.util.List;

public interface LikeService {
    List<LikeDto> getAll();
    LikeDto getById(Long id);
    LikeDto create(LikeCreateDto likeCreateDto);
    boolean delete(Long id);

}
