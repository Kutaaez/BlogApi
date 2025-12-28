package kz.seppaku.postmanBlog.controllers;

import kz.seppaku.postmanBlog.dto.request.ReviewCreateDto;
import kz.seppaku.postmanBlog.dto.response.ReviewDto;
import kz.seppaku.postmanBlog.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDto> getById(@PathVariable Long id) {
        ReviewDto reviewDto = reviewService.getById(id);
        if (reviewDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reviewDto);
    }

    @PostMapping
    public ResponseEntity<ReviewDto> create(@RequestBody ReviewCreateDto reviewCreateDto) {
        ReviewDto created = reviewService.create(reviewCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDto> update(@PathVariable Long id, @RequestBody ReviewDto reviewDto) {
        ReviewDto updated = reviewService.update(id, reviewDto);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = reviewService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}