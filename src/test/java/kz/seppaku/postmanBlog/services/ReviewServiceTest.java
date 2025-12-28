package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.ReviewCreateDto;
import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.dto.response.ReviewDto;
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
class ReviewServiceTest {
    @Autowired private ReviewService reviewService;
    @Autowired private ThreadService threadService;
    @Autowired private UserRepository userRepository;
    private Long threadId;

    @BeforeEach
    void setUp() {
        String email = "review@test.com";
        if(userRepository.findByEmail(email).isEmpty()){
            User user = new User();
            user.setEmail(email);
            user.setPassword("pass");
            user.setUsername("Reviewer");
            userRepository.save(user);
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(email, "pass", new ArrayList<>())
        );
        ThreadDto t = threadService.create(new ThreadCreateDto("T", "B", null, new ArrayList<>()));
        threadId = t.getId();
    }

    @Test
    void createReviewTest() {
        ReviewCreateDto dto = new ReviewCreateDto("Nice", threadId);
        ReviewDto created = reviewService.create(dto);
        Assertions.assertNotNull(created);
        Assertions.assertEquals("Nice", created.getText());
    }

    @Test
    void updateReviewTest() {
        ReviewDto created = reviewService.create(new ReviewCreateDto("Bad", threadId));
        created.setText("Good");
        ReviewDto updated = reviewService.update(created.getId(), created);
        Assertions.assertEquals("Good", updated.getText());
    }

    @Test
    void deleteReviewTest() {
        ReviewDto created = reviewService.create(new ReviewCreateDto("Del", threadId));
        boolean res = reviewService.delete(created.getId());
        Assertions.assertTrue(res);
    }
}