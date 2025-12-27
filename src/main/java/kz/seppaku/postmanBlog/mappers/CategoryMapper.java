package kz.seppaku.postmanBlog.mappers;

import kz.seppaku.postmanBlog.dto.response.CategoryDto;
import kz.seppaku.postmanBlog.dto.request.CategoryCreateDto;
import kz.seppaku.postmanBlog.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryCreateDto dto);

    List<CategoryDto> toDtoList(List<Category> categories);
}