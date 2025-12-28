package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.request.LikeCreateDto;
import kz.seppaku.postmanBlog.dto.response.LikeDto;
import kz.seppaku.postmanBlog.entities.Like;
import kz.seppaku.postmanBlog.entities.Thread;
import kz.seppaku.postmanBlog.entities.User;
import kz.seppaku.postmanBlog.mapper.LikeMapper;
import kz.seppaku.postmanBlog.repositories.LikeRepository;
import kz.seppaku.postmanBlog.repositories.ThreadRepository;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import kz.seppaku.postmanBlog.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;

    @Override
    public List<LikeDto> getAll() {
        return likeRepository.findAll().stream()
                .map(likeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LikeDto getById(Long id) {
        return likeMapper.toDto(likeRepository.findById(id).orElse(null));
    }

    @Override
    public LikeDto create(LikeCreateDto likeCreateDto) {
        if (likeCreateDto == null) return null;

        Like like = new Like();

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        like.setUser(user);

        if (likeCreateDto.getThreadId() != null) {
            Thread thread = threadRepository.findById(likeCreateDto.getThreadId()).orElse(null);
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