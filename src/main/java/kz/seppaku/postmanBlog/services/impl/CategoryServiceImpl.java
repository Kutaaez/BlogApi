package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.response.CategoryDto;
import kz.seppaku.postmanBlog.dto.request.CategoryCreateDto;
import kz.seppaku.postmanBlog.entities.Category;
import kz.seppaku.postmanBlog.mapper.CategoryMapper;
import kz.seppaku.postmanBlog.repositories.CategoryRepository;
import kz.seppaku.postmanBlog.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryMapper.toDto(categoryRepository.findById(id).orElse(null));
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