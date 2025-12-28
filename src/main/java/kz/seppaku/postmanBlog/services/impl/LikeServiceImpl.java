package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.request.LikeCreateDto;
import kz.seppaku.postmanBlog.dto.response.LikeDto;
import kz.seppaku.postmanBlog.mapper.LikeMapper;
import kz.seppaku.postmanBlog.repositories.LikeRepository;
import kz.seppaku.postmanBlog.repositories.ThreadRepository;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import kz.seppaku.postmanBlog.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    @Override
    public List<LikeDto> getAll() {
        List<kz.seppaku.postmanBlog.entities.Like> likes = likeRepository.findAll();
        List<LikeDto> dtos = new ArrayList<>();
        for (kz.seppaku.postmanBlog.entities.Like like : likes) {
            dtos.add(likeMapper.toDto(like));
        }
        return dtos;
    }

    @Override
    public LikeDto getById(Long id) {
        return likeMapper.toDto(likeRepository.findById(id).orElse(null));
    }

    @Override
    public LikeDto create(LikeCreateDto likeCreateDto) {
        if (likeCreateDto == null) return null;

        kz.seppaku.postmanBlog.entities.Like like = new kz.seppaku.postmanBlog.entities.Like();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        kz.seppaku.postmanBlog.entities.User user = null;
        List<kz.seppaku.postmanBlog.entities.User> users = userRepository.findAll();
        for (kz.seppaku.postmanBlog.entities.User u : users) {
            if (u.getEmail().equals(email)) {
                user = u;
                break;
            }
        }

        if (user != null) {
            like.setUser(user);
        }

        if (likeCreateDto.getThreadId() != null) {
            kz.seppaku.postmanBlog.entities.Thread thread = threadRepository.findById(likeCreateDto.getThreadId()).orElse(null);
            like.setThread(thread);
        }

        return likeMapper.toDto(likeRepository.save(like));
    }

    @Override
    public boolean delete(Long id) {
        if (likeRepository.existsById(id)) {
            likeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}