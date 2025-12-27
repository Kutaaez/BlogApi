package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.response.ThreadDto;
import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.entities.Category;
import kz.seppaku.postmanBlog.entities.Thread;
import kz.seppaku.postmanBlog.entities.User;
import kz.seppaku.postmanBlog.mappers.ThreadMapper;
import kz.seppaku.postmanBlog.repositories.CategoryRepository;
import kz.seppaku.postmanBlog.repositories.ThreadRepository;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import kz.seppaku.postmanBlog.services.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

    private final ThreadRepository threadRepository;
    private final ThreadMapper threadMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ThreadDto> getAll() {
        List<Thread> threads = threadRepository.findAll();
        List<ThreadDto> dtos = new ArrayList<>();
        for (Thread thread : threads) {
            dtos.add(threadMapper.toDto(thread));
        }
        return dtos;
    }

    @Override
    public ThreadDto getById(Long id) {
        return threadMapper.toDto(threadRepository.findById(id).orElse(null));
    }

    @Override
    public ThreadDto create(ThreadCreateDto threadCreateDto) {
        if (threadCreateDto == null) return null;

        Thread thread = new Thread();
        thread.setTitle(threadCreateDto.getTitle());
        thread.setText(threadCreateDto.getText());

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
            thread.setUser(user);
        }

        if (threadCreateDto.getCategoryIds() != null) {
            List<Category> categories = new ArrayList<>();
            for (Long catId : threadCreateDto.getCategoryIds()) {
                Category cat = categoryRepository.findById(catId).orElse(null);
                if (cat != null) {
                    categories.add(cat);
                }
            }
            thread.setCategories(categories);
        }

        return threadMapper.toDto(threadRepository.save(thread));
    }

    @Override
    public ThreadDto update(Long id, ThreadCreateDto threadCreateDto) {
        Thread existing = threadRepository.findById(id).orElse(null);
        if (existing == null || threadCreateDto == null) {
            return null;
        }

        existing.setTitle(threadCreateDto.getTitle());
        existing.setText(threadCreateDto.getText());

        if (threadCreateDto.getCategoryIds() != null) {
            List<Category> categories = new ArrayList<>();
            for (Long catId : threadCreateDto.getCategoryIds()) {
                Category cat = categoryRepository.findById(catId).orElse(null);
                if (cat != null) {
                    categories.add(cat);
                }
            }
            existing.setCategories(categories);
        }

        return threadMapper.toDto(threadRepository.save(existing));
    }

    @Override
    public boolean delete(Long id) {
        if (threadRepository.existsById(id)) {
            threadRepository.deleteById(id);
            return true;
        }
        return false;
    }
}