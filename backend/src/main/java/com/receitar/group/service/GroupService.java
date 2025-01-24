package com.receitar.group.service;

import com.receitar.client.model.User;
import com.receitar.client.service.UserService;
import com.receitar.group.dto.GroupCreateDto;
import com.receitar.group.model.Group;
import com.receitar.group.model.GroupUser;
import com.receitar.group.repository.GroupRepository;
import com.receitar.group.repository.GroupUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserService userService;

    private final GroupUserRepository groupUserRepository;

    public void create(GroupCreateDto groupCreateDto) {
        User user = userService.getById(groupCreateDto.userId());
        Group group = new Group();

        group.setCreatedBy(groupCreateDto.userId());
        group.setDescription(groupCreateDto.description());
        group.setName(groupCreateDto.name());
        group.setCreatedDate(LocalDate.now());

        groupRepository.save(group);

        addUserToGroup(group, user);
    }

    public void addUserToGroup(UUID groupId, UUID userId) {
        User user = userService.getById(userId);
        Group group = groupRepository.findById(groupId).orElseThrow();

        addUserToGroup(group, user);
    }

    public Group findById(UUID id) {
        return groupRepository.findById(id).orElseThrow();
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public List<User> getAllUsersByGroup(UUID groupId) {
        Group group = findById(groupId);
        return group.getGroupUsers().stream()
                .map(GroupUser::getUser)
                .toList();
    }

    private void addUserToGroup(Group group, User user) {
        GroupUser groupUser = new GroupUser();

        groupUser.setGroup(group);
        groupUser.setUser(user);

        groupUserRepository.save(groupUser);
    }
}
