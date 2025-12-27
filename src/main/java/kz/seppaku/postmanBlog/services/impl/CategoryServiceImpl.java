package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.response.CategoryDto;
import kz.seppaku.postmanBlog.dto.request.CategoryCreateDto;
import kz.seppaku.postmanBlog.entities.Category;
import kz.seppaku.postmanBlog.mappers.CategoryMapper;
import kz.seppaku.postmanBlog.repositories.CategoryRepository;
import kz.seppaku.postmanBlog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll() {
        List<Category> entities = categoryRepository.findAll();
        List<CategoryDto> dtos = new ArrayList<>();
        for (Category entity : entities) {
            dtos.add(categoryMapper.toDto(entity));
        }
        return dtos;
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto create(CategoryCreateDto categoryCreateDto) {
        if (categoryCreateDto == null) return null;
        Category category = categoryMapper.toEntity(categoryCreateDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(Long id, CategoryCreateDto categoryCreateDto) {
        Category existing = categoryRepository.findById(id).orElse(null);
        if (existing == null || categoryCreateDto == null) {
            return null;
        }
        existing.setName(categoryCreateDto.getName());
        return categoryMapper.toDto(categoryRepository.save(existing));
    }

    @Override
    public boolean delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}