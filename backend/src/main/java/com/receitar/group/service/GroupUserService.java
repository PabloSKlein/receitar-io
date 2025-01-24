package com.receitar.group.service;

import com.receitar.client.model.User;
import com.receitar.client.service.UserService;
import com.receitar.common.exception.NotFoundException;
import com.receitar.group.dto.ChangeAdministratorDto;
import com.receitar.group.model.GroupUser;
import com.receitar.group.repository.GroupUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GroupUserService {
    private final GroupUserRepository groupUserRepository;
    private final UserService userService;

    private final GroupService groupService;

    public void changeAdministrator(ChangeAdministratorDto changeAdministratorDto) {
        groupService.verifyIfUserIsAdmin(changeAdministratorDto.groupId(), changeAdministratorDto.systemUserId());

        User user = userService.getById(changeAdministratorDto.userId());

        List<GroupUser> groupUsers = user.getGroupUsers();

        GroupUser groupUser = groupUsers.stream()
                .filter(it -> it.getGroup().getId().equals(changeAdministratorDto.groupId()))
                .findFirst().orElseThrow(() -> new NotFoundException("group user"));

        groupUser.setAdministrator(changeAdministratorDto.isAdministrator());

        groupUserRepository.save(groupUser);
    }
}
