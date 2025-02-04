package com.receitar.group.service;

import com.receitar.client.model.User;
import com.receitar.client.service.UserService;
import com.receitar.common.exception.BusinessException;
import com.receitar.common.exception.NotFoundException;
import com.receitar.group.dto.GroupCreateDto;
import com.receitar.group.dto.GroupUserDto;
import com.receitar.group.model.Group;
import com.receitar.group.model.GroupUser;
import com.receitar.group.repository.GroupRepository;
import com.receitar.group.repository.GroupUserRepository;
import jakarta.transaction.Transactional;
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

    public Group create(GroupCreateDto groupCreateDto) {
        User user = userService.getById(groupCreateDto.userId());

        Group group = new Group();

        group.setCreatedBy(groupCreateDto.userId());
        group.setDescription(groupCreateDto.description());
        group.setName(groupCreateDto.name());
        group.setCreatedDate(LocalDate.now());

        groupRepository.save(group);

        return addUserToGroup(group, user, true).getGroup();
    }

    public void addUserToGroup(UUID groupId, UUID userId, UUID systemUserId) {
        verifyIfUserIsAdmin(groupId, systemUserId);

        User user = userService.getById(userId);
        Group group = groupRepository.findById(groupId).orElseThrow();

        addUserToGroup(group, user, false);
    }

    public Group findById(UUID id) {
        return groupRepository.findById(id).orElseThrow(()->new NotFoundException("Group"));
    }

    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    public List<GroupUser> getAllUsersByGroup(UUID groupId) {
        Group group = findById(groupId);
        return group.getGroupUsers().stream()
                .toList();
    }

    @Transactional
    public void removeUserFromGroup(GroupUserDto groupUserDto) {
        verifyIfUserIsAdmin(groupUserDto.groupId(), groupUserDto.systemUserId());

        User user = userService.getById(groupUserDto.userId());
        GroupUser groupUser = user.getGroupUsers().stream()
                .filter(it -> it.getGroup().getId().equals(groupUserDto.groupId()))
                .findFirst()
                .orElseThrow(() -> new BusinessException("User is not in this group."));


        groupUserRepository.delete(groupUser);
    }

    private GroupUser addUserToGroup(Group group, User user, Boolean isAdministrator) {
        GroupUser groupUser = new GroupUser();

        groupUser.setGroup(group);
        groupUser.setUser(user);
        groupUser.setAdministrator(isAdministrator);

        group.getGroupUsers().add(groupUser);

        return groupUserRepository.save(groupUser);
    }

    public void verifyIfUserIsAdmin(UUID groupId, UUID systemUserId) {
        User systemUser = userService.getById(systemUserId);

        boolean isAdmin = systemUser.getGroupUsers().stream()
                .anyMatch(groupUser -> groupUser.getGroup().getId().equals(groupId) && groupUser.isAdministrator());

        if (!isAdmin) {
            throw new BusinessException("System user is not admin.");
        }
    }

    public void delete(UUID groupId, UUID systemUserId) {
        verifyIfUserIsAdmin(groupId, systemUserId);

        Group group = findById(groupId);

        List<GroupUser> groupUsers = group.getGroupUsers();
        groupUserRepository.deleteAll(groupUsers);
        groupRepository.delete(group);
    }
}
