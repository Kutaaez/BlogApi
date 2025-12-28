package kz.seppaku.postmanBlog.controllers;

import kz.seppaku.postmanBlog.dto.request.UserCreateDto;
import kz.seppaku.postmanBlog.dto.response.UserDto;
import kz.seppaku.postmanBlog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        UserDto user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDto> updateProfile(@RequestBody UserCreateDto userCreateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto currentUser = userService.getByEmail(email);

        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }

        UserDto updatedUser = userService.update(currentUser.getId(), userCreateDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody Map<String, String> request) {
        String oldPassword = request.get("oldPassword");
        String newPassword = request.get("newPassword");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        UserDto currentUser = userService.getByEmail(email);
        if (currentUser == null) {
            return ResponseEntity.notFound().build();
        }
        userService.changePassword(currentUser.getId(), oldPassword, newPassword);

        return ResponseEntity.ok().build();
    }
}