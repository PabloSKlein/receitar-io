package com.receitar.group.repository;

import com.receitar.group.model.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupUserRepository extends JpaRepository<GroupUser, UUID> {
}
