package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.response.UserDto;
import kz.seppaku.postmanBlog.dto.request.UserCreateDto;
import kz.seppaku.postmanBlog.entities.Role;
import kz.seppaku.postmanBlog.entities.User;
import kz.seppaku.postmanBlog.mapper.UserMapper;
import kz.seppaku.postmanBlog.repositories.RoleRepository;
import kz.seppaku.postmanBlog.repositories.UserRepository;
import kz.seppaku.postmanBlog.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserDto getByEmail(String email) {
        return userMapper.toDto(userRepository.findByEmail(email).orElse(null));
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        if (userCreateDto == null) return null;

        if (userRepository.findByEmail(userCreateDto.getEmail()).isPresent()) {
            return null;
        }

        User newUser = new User();
        newUser.setUsername(userCreateDto.getUsername());
        newUser.setEmail(userCreateDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        newUser.setBanned(false);

        List<Role> roles = new ArrayList<>();

        if (userCreateDto.getRoles() != null && !userCreateDto.getRoles().isEmpty()) {
            for (String roleName : userCreateDto.getRoles()) {
                roleRepository.findByName(roleName).ifPresent(roles::add);
            }
        }

        if (roles.isEmpty()) {
            roleRepository.findByName("ROLE_USER").ifPresent(roles::add);
        }

        newUser.setRoles(roles);

        return userMapper.toDto(userRepository.save(newUser));
    }

    @Override
    public UserDto update(Long id, UserCreateDto userCreateDto) {
        User old = userRepository.findById(id).orElse(null);
        if (old == null || userCreateDto == null) {
            return null;
        }

        old.setUsername(userCreateDto.getUsername());
        old.setEmail(userCreateDto.getEmail());

        if (userCreateDto.getPassword() != null && !userCreateDto.getPassword().isEmpty()) {
            old.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        }

        return userMapper.toDto(userRepository.save(old));
    }

    @Override
    public boolean delete(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void changeRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }

        roleRepository.findByName(roleName).ifPresent(role -> {
            List<Role> newRoles = new ArrayList<>();
            newRoles.add(role);
            user.setRoles(newRoles);
            userRepository.save(user);
        });
    }

    @Override
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (passwordEncoder.matches(oldPassword, user.getPassword())) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            }
        }
    }

    @Override
    public void banUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setBanned(true);
            userRepository.save(user);
        }
    }

    @Override
    public void unbanUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            user.setBanned(false);
            userRepository.save(user);
        }
    }
}