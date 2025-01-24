package com.receitar.group.controller;

import com.receitar.client.dto.UserViewDto;
import com.receitar.group.dto.GroupAddDto;
import com.receitar.group.dto.GroupCreateDto;
import com.receitar.group.dto.GroupUserDto;
import com.receitar.group.dto.GroupViewDto;
import com.receitar.group.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    void create(@RequestBody GroupCreateDto groupCreateDto) {
        groupService.create(groupCreateDto);
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
        groupService.addUserToGroup(groupAddDto.groupId(), groupAddDto.userId());
    }

    @GetMapping("/{id}/users")
    List<UserViewDto> getAllUsersByGroup(@PathVariable UUID id) {
        return groupService.getAllUsersByGroup(id).stream()
                .map(UserViewDto::new)
                .toList();
    }

    @DeleteMapping("/users")
    void removeUserFromGroup(GroupUserDto groupUserDto){
        //TODO
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable UUID id){
        //TODO validate that all GroupUsers related to this group, are also deleted.
    }


}
