package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.LikeCreateDto;
import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.dto.response.LikeDto;
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

@SpringBootTest
class LikeServiceTest {
    @Autowired private LikeService likeService;
    @Autowired private ThreadService threadService;
    @Autowired private UserRepository userRepository;
    private Long threadId;

    @BeforeEach
    void setUp() {
        String email = "like@test.com";
        if(userRepository.findByEmail(email).isEmpty()){
            User user = new User();
            user.setEmail(email);
            user.setPassword("pass");
            user.setUsername("Liker");
            userRepository.save(user);
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(email, "pass", new ArrayList<>())
        );
        ThreadDto t = threadService.create(new ThreadCreateDto("L", "B", null, new ArrayList<>()));
        threadId = t.getId();
    }

    @Test
    void createLikeTest() {
        LikeCreateDto dto = new LikeCreateDto(threadId);
        LikeDto created = likeService.create(dto);
        Assertions.assertNotNull(created);
        Assertions.assertEquals(threadId, created.getThreadId());
    }

    @Test
    void deleteLikeTest() {
        LikeDto created = likeService.create(new LikeCreateDto(threadId));
        boolean res = likeService.delete(created.getId());
        Assertions.assertTrue(res);
        Assertions.assertNull(likeService.getById(created.getId()));
    }

    @Test
    void getAllLikesTest() {
        likeService.create(new LikeCreateDto(threadId));
        Assertions.assertFalse(likeService.getAll().isEmpty());
    }
}