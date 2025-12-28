package kz.seppaku.postmanBlog.controllers;

import kz.seppaku.postmanBlog.dto.request.LikeCreateDto;
import kz.seppaku.postmanBlog.dto.response.LikeDto;
import kz.seppaku.postmanBlog.services.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<List<LikeDto>> getAll() {
        return ResponseEntity.ok(likeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LikeDto> getById(@PathVariable Long id) {
        LikeDto likeDto = likeService.getById(id);
        if (likeDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(likeDto);
    }

    @PostMapping
    public ResponseEntity<LikeDto> create(@RequestBody LikeCreateDto likeCreateDto) {
        LikeDto created = likeService.create(likeCreateDto);
        if (created == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = likeService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}