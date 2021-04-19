package com.example.bookstore.frontoffice.user.service;

import com.example.bookstore.frontoffice.user.dto.UserDto;
import com.example.bookstore.frontoffice.user.mapper.UserMapper;
import com.example.bookstore.frontoffice.user.model.ERole;
import com.example.bookstore.frontoffice.user.model.Role;
import com.example.bookstore.frontoffice.user.model.User;
import com.example.bookstore.frontoffice.user.repository.RoleRepository;
import com.example.bookstore.frontoffice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final UserMapper userMapper;

    private final PasswordEncoder encoder;

    private User findById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    public List<UserDto> findAll() {

        return userRepository.findAll().stream().map(
                userMapper::userMinimalFromUser)
                .collect(toList());

    }

    public void delete(int id) {

        userRepository.deleteById(id);
    }

    public void deleteAll() {

        userRepository.deleteAll();
    }

    public UserDto create(UserDto userDto) {

        Set<Role> roles = new HashSet<>();

        Role defaultRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RuntimeException("Cannot find USER role"));

        roles.add(defaultRole);

        User user = userMapper.fromDto(userDto);

        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(roles);

        return userMapper.userMinimalFromUser(userRepository.save(user));
    }

    public UserDto update(Integer id, UserDto userDto) {

        User user = findById(id);

        user.setUsername(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encoder.encode(userDto.getPassword()));

        return userMapper.userMinimalFromUser(
                userRepository.save(user)
        );
    }

    public List<UserDto> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }
}