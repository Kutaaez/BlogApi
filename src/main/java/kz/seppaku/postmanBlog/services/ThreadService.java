package kz.seppaku.postmanBlog.services;

import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.dto.response.ThreadDto;

import java.util.List;

public interface ThreadService {
    List<ThreadDto> getAll();
    ThreadDto getById(Long id);
    ThreadDto create(ThreadCreateDto threadCreateDto);
    ThreadDto update(Long id, ThreadCreateDto threadreateDto);
    boolean delete(Long id);
}
