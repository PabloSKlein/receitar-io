package com.receitar.client.controller;

import com.receitar.client.dto.UserCreateDto;
import com.receitar.client.dto.UserViewDto;
import com.receitar.client.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    UserViewDto create(@RequestBody UserCreateDto userDto) {
        return new UserViewDto(userService.create(userDto));
    }

    @GetMapping("/{id}")
    UserViewDto getById(@PathVariable UUID id) {
        return new UserViewDto(userService.getById(id));
    }

    @GetMapping
    List<UserViewDto> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable UUID id) {
        userService.deleteById(id);
    }

    @GetMapping("/name")
    UserViewDto getUserByName(@RequestBody UserCreateDto userCreateDto) {
        return new UserViewDto(userService.getUserByName(userCreateDto.name()));
    }

}
