package com.receitar.group.controller;

import com.receitar.group.dto.*;
import com.receitar.group.service.GroupService;
import com.receitar.group.service.GroupUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;
    private final GroupUserService groupUserService;

    @PostMapping
    GroupViewDto create(@RequestBody GroupCreateDto groupCreateDto) {
        return new GroupViewDto(groupService.create(groupCreateDto));
    }

    @GetMapping
    List<GroupViewDto> getAll() {
        return groupService.getAll().stream()
                .map(GroupViewDto::new)
                .toList();
    }

    @GetMapping("/{id}")
    GroupViewDto findById(@PathVariable UUID id) {
        return new GroupViewDto(groupService.findById(id));
    }

    @PostMapping("/users")
    void addUserToGroup(@RequestBody GroupAddDto groupAddDto) {
        groupService.addUserToGroup(groupAddDto.groupId(), groupAddDto.userId(), groupAddDto.systemUserId());
    }

    @GetMapping("/{id}/users")
    List<GroupUserViewDto> getAllUsersByGroup(@PathVariable UUID id) {
        return groupService.getAllUsersByGroup(id).stream()
                .map(GroupUserViewDto::new)
                .toList();
    }

    @PostMapping("/users/admins")
    void changeAdministrator(@RequestBody ChangeAdministratorDto changeAdministratorDto) {
        groupUserService.changeAdministrator(changeAdministratorDto);
    }

    @DeleteMapping("/users")
    void removeUserFromGroup(@RequestBody GroupUserDto groupUserDto) {
        groupService.removeUserFromGroup(groupUserDto);
    }

    @DeleteMapping
    void delete(@RequestBody GroupDeleteDto groupDeleteDto) {
        groupService.delete(groupDeleteDto.id(), groupDeleteDto.systemUserId());
    }
}
