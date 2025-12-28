package kz.seppaku.postmanBlog.controllers;

import kz.seppaku.postmanBlog.dto.request.ThreadCreateDto;
import kz.seppaku.postmanBlog.dto.response.ThreadDto;
import kz.seppaku.postmanBlog.services.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class ThreadController {

    private final ThreadService threadService;

    @GetMapping
    public ResponseEntity<List<ThreadDto>> getAll() {
        return ResponseEntity.ok(threadService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThreadDto> getById(@PathVariable Long id) {
        ThreadDto threadDto = threadService.getById(id);
        if (threadDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(threadDto);
    }

    @PostMapping
    public ResponseEntity<ThreadDto> create(@RequestBody ThreadCreateDto threadCreateDto) {
        ThreadDto created = threadService.create(threadCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThreadDto> update(@PathVariable Long id, @RequestBody ThreadCreateDto threadCreateDto) {
        ThreadDto updated = threadService.update(id, threadCreateDto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = threadService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}