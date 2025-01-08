package com.receitar.client.service;

import com.receitar.client.dto.UserCreateDto;
import com.receitar.client.dto.UserViewDto;
import com.receitar.client.model.User;
import com.receitar.client.repository.UserRepository;
import com.receitar.common.exception.NotFoundException;
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
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User"));
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
