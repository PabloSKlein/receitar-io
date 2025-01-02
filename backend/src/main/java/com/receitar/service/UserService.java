package com.receitar.service;

import com.receitar.dto.UserCreateDto;
import com.receitar.dto.UserViewDto;
import com.receitar.model.User;
import com.receitar.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User create(UserCreateDto userDto) {
        User user = new User();
        user.setName(userDto.name());

        return userRepository.save(user);
    }

    public UserViewDto getById(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        return new UserViewDto(user.getId(), user.getName());
    }

    public List<UserViewDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserViewDto(user.getId(), user.getName()))
                .toList();
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
}
