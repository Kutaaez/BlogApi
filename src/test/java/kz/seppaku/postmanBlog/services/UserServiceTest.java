    package kz.seppaku.postmanBlog.services;

    import kz.seppaku.postmanBlog.dto.request.UserCreateDto;
    import kz.seppaku.postmanBlog.dto.response.UserDto;
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.context.SpringBootTest;
    import java.util.ArrayList;
    import java.util.List;

    @SpringBootTest
    class UserServiceTest {
        @Autowired private UserService userService;

        @Test
        void createUserTest() {
            UserCreateDto dto = new UserCreateDto();
            dto.setEmail("new@test.com");
            dto.setUsername("NewUser");
            dto.setPassword("12345");
            dto.setRoles(new ArrayList<>());
            UserDto created = userService.create(dto);
            Assertions.assertNotNull(created);
            Assertions.assertEquals("new@test.com", created.getEmail());
        }

        @Test
        void getAllUsersTest() {
            List<UserDto> list = userService.getAll();
            Assertions.assertNotNull(list);
        }

        @Test
        void getUserByIdTest() {
            UserCreateDto dto = new UserCreateDto();
            dto.setEmail("id@test.com");
            dto.setUsername("IdUser");
            dto.setPassword("123");
            UserDto created = userService.create(dto);
            UserDto found = userService.getById(created.getId());
            Assertions.assertNotNull(found);
            Assertions.assertEquals(created.getId(), found.getId());
        }

        @Test
        void updateUserTest() {
            UserCreateDto dto = new UserCreateDto();
            dto.setEmail("upd@test.com");
            dto.setUsername("UpdUser");
            dto.setPassword("123");
            UserDto created = userService.create(dto);
            UserCreateDto updateDto = new UserCreateDto();
            updateDto.setUsername("UpdatedName");
            updateDto.setEmail("upd@test.com");
            UserDto updated = userService.update(created.getId(), updateDto);
            Assertions.assertEquals("UpdatedName", updated.getUsername());
        }

        @Test
        void deleteUserTest() {
            UserCreateDto dto = new UserCreateDto();
            dto.setEmail("del@test.com");
            dto.setUsername("DelUser");
            dto.setPassword("123");
            UserDto created = userService.create(dto);
            boolean isDeleted = userService.delete(created.getId());
            Assertions.assertTrue(isDeleted);
            Assertions.assertNull(userService.getById(created.getId()));
        }
    }