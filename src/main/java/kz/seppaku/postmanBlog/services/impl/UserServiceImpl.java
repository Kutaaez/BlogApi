package kz.seppaku.postmanBlog.services.impl;

import kz.seppaku.postmanBlog.dto.response.UserDto;
import kz.seppaku.postmanBlog.dto.request.UserCreateDto;
import kz.seppaku.postmanBlog.entities.Role;
import kz.seppaku.postmanBlog.entities.User;
import kz.seppaku.postmanBlog.mappers.UserMapper;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(userMapper.toDto(user));
        }
        return dtos;
    }

    @Override
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElse(null));
    }

    @Override
    public UserDto getByEmail(String email) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return userMapper.toDto(user);
            }
        }
        return null;
    }

    @Override
    public UserDto create(UserCreateDto userCreateDto) {
        if (userCreateDto == null) return null;

        List<User> users = userRepository.findAll();
        for (User u : users) {
            if (u.getEmail().equals(userCreateDto.getEmail())) {
                return null;
            }
        }

        User newUser = new User();
        newUser.setUsername(userCreateDto.getUsername());
        newUser.setEmail(userCreateDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        List<Role> roles = new ArrayList<>();

        if (userCreateDto.getRoles() != null && !userCreateDto.getRoles().isEmpty()) {
            List<Role> allRoles = roleRepository.findAll();
            for (String roleName : userCreateDto.getRoles()) {
                for (Role r : allRoles) {
                    if (r.getName().equals(roleName)) {
                        roles.add(r);
                        break;
                    }
                }
            }
        }

        if (roles.isEmpty()) {
            List<Role> allRoles = roleRepository.findAll();
            for (Role r : allRoles) {
                if (r.getName().equals("ROLE_USER")) {
                    roles.add(r);
                    break;
                }
            }
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
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public void changeRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }

        Role targetRole = null;
        List<Role> allRoles = roleRepository.findAll();
        for (Role r : allRoles) {
            if (r.getName().equals(roleName)) {
                targetRole = r;
                break;
            }
        }

        if (targetRole != null) {
            List<Role> newRoles = new ArrayList<>();
            newRoles.add(targetRole);
            user.setRoles(newRoles);
            userRepository.save(user);
        }
    }
}