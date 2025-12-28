package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.dto.response.ThreadDto;
import kz.seppaku.postmanBlog.entities.User;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class ThreadServiceTest {
    @Autowired private ThreadService threadService;
    @Autowired private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        if(userRepository.findByEmail("thread@test.com").isEmpty()){
            User user = new User();
            user.setEmail("thread@test.com");
            user.setPassword("pass");
            user.setUsername("ThreadUser");
            userRepository.save(user);
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("thread@test.com", "pass", new ArrayList<>())
        );
    }

    @Test
    void createThreadTest() {
        ThreadCreateDto dto = new ThreadCreateDto();
        dto.setTitle("Title");
        dto.setText("Text");
        dto.setCategoryIds(new ArrayList<>());
        ThreadDto created = threadService.create(dto);
        Assertions.assertNotNull(created);
        Assertions.assertEquals("Title", created.getTitle());
    }

    @Test
    void getAllThreadsTest() {
        ThreadCreateDto dto = new ThreadCreateDto("T2", "Tx2", null, new ArrayList<>());
        threadService.create(dto);
        List<ThreadDto> list = threadService.getAll();
        Assertions.assertNotNull(list);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    void updateThreadTest() {
        ThreadDto created = threadService.create(new ThreadCreateDto("Old", "Txt", null, new ArrayList<>()));
        ThreadCreateDto updateDto = new ThreadCreateDto("New", "NewTxt", null, new ArrayList<>());
        ThreadDto updated = threadService.update(created.getId(), updateDto);
        Assertions.assertEquals("New", updated.getTitle());
    }

    @Test
    void deleteThreadTest() {
        ThreadDto created = threadService.create(new ThreadCreateDto("Del", "Del", null, new ArrayList<>()));
        boolean res = threadService.delete(created.getId());
        Assertions.assertTrue(res);
        Assertions.assertNull(threadService.getById(created.getId()));
    }
}