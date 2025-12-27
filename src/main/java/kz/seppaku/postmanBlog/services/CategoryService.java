package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.CategoryCreateDto;
import kz.seppaku.postmanBlog.dto.response.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll();
    CategoryDto getById(Long id);
    CategoryDto create(CategoryCreateDto categoryCreateDto);
    CategoryDto update(Long id, CategoryCreateDto categoryCreateDto);
    boolean delete(Long id);
}
