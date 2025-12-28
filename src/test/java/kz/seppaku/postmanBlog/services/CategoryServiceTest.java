package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.CategoryCreateDto;
import kz.seppaku.postmanBlog.dto.response.CategoryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

@SpringBootTest
class CategoryServiceTest {
    @Autowired private CategoryService categoryService;

    @Test
    void createCategoryTest() {
        CategoryCreateDto dto = new CategoryCreateDto("Java");
        CategoryDto created = categoryService.create(dto);
        Assertions.assertNotNull(created);
        Assertions.assertEquals("Java", created.getName());
    }

    @Test
    void getAllCategoriesTest() {
        categoryService.create(new CategoryCreateDto("Spring"));
        List<CategoryDto> list = categoryService.getAll();
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void getCategoryByIdTest() {
        CategoryDto created = categoryService.create(new CategoryCreateDto("TestID"));
        CategoryDto found = categoryService.getById(created.getId());
        Assertions.assertNotNull(found);
        Assertions.assertEquals(created.getId(), found.getId());
    }

    @Test
    void updateCategoryTest() {
        CategoryDto created = categoryService.create(new CategoryCreateDto("OldName"));
        CategoryCreateDto updateDto = new CategoryCreateDto("NewName");
        CategoryDto updated = categoryService.update(created.getId(), updateDto);
        Assertions.assertEquals("NewName", updated.getName());
    }

    @Test
    void deleteCategoryTest() {
        CategoryDto created = categoryService.create(new CategoryCreateDto("DeleteMe"));
        boolean res = categoryService.delete(created.getId());
        Assertions.assertTrue(res);
        Assertions.assertNull(categoryService.getById(created.getId()));
    }
}