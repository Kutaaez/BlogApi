package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.response.ThreadDto;
import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.entities.Category;
import kz.seppaku.postmanBlog.entities.Thread;
import kz.seppaku.postmanBlog.entities.User;
import kz.seppaku.postmanBlog.mapper.ThreadMapper;
import kz.seppaku.postmanBlog.repositories.CategoryRepository;
import kz.seppaku.postmanBlog.repositories.ThreadRepository;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import kz.seppaku.postmanBlog.services.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThreadServiceImpl implements ThreadService {

    private final ThreadRepository threadRepository;
    private final ThreadMapper threadMapper;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<ThreadDto> getAll() {
        return threadRepository.findAll().stream()
                .map(threadMapper::toDto)
                .collect(Collectors.toList());
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        thread.setUser(user);

        if (threadCreateDto.getCategoryIds() != null && !threadCreateDto.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(threadCreateDto.getCategoryIds());
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
            List<Category> categories = categoryRepository.findAllById(threadCreateDto.getCategoryIds());
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