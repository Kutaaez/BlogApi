package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.response.ReviewDto;
import kz.seppaku.postmanBlog.dto.request.ReviewCreateDto;
import kz.seppaku.postmanBlog.entities.Review;
import kz.seppaku.postmanBlog.entities.Thread;
import kz.seppaku.postmanBlog.entities.User;
import kz.seppaku.postmanBlog.mappers.ReviewMapper;
import kz.seppaku.postmanBlog.repositories.ReviewRepository;
import kz.seppaku.postmanBlog.repositories.ThreadRepository;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import kz.seppaku.postmanBlog.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    @Override
    public List<ReviewDto> getAll() {
        List<Review> reviews = reviewRepository.findAll();
        List<ReviewDto> dtos = new ArrayList<>();
        for (Review review : reviews) {
            dtos.add(reviewMapper.toDto(review));
        }
        return dtos;
    }

    @Override
    public ReviewDto getById(Long id) {
        return reviewMapper.toDto(reviewRepository.findById(id).orElse(null));
    }

    @Override
    public ReviewDto create(ReviewDto reviewDto) {
        return null;
    }

    public ReviewDto create(ReviewCreateDto reviewCreateDto) {
        if (reviewCreateDto == null) return null;

        Review review = new Review();
        review.setText(reviewCreateDto.getText());

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = null;
        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getEmail().equals(email)) {
                user = u;
                break;
            }
        }

        if (user != null) {
            review.setUser(user);
        }

        if (reviewCreateDto.getThreadId() != null) {
            Thread thread = threadRepository.findById(reviewCreateDto.getThreadId()).orElse(null);
            review.setThread(thread);
        }

        return reviewMapper.toDto(reviewRepository.save(review));
    }

    @Override
    public ReviewDto update(Long id, ReviewDto reviewDto) {
        Review existing = reviewRepository.findById(id).orElse(null);
        if (existing == null || reviewDto == null) {
            return null;
        }
        existing.setText(reviewDto.getText());
        return reviewMapper.toDto(reviewRepository.save(existing));
    }

    @Override
    public boolean delete(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }
        return false;
    }
}