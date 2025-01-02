package com.receitar.controller;

import com.receitar.dto.UserCreateDto;
import com.receitar.dto.UserViewDto;
import com.receitar.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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
        return userService.getById(id);
    }

    @GetMapping
    List<UserViewDto> getAll() {
        return userService.getAll();
    }

    @DeleteMapping("/{id}")
    void deleteById(@PathVariable UUID id){
        userService.deleteById(id);
    }

}
