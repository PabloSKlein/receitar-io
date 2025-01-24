package com.receitar.group.dto;

import java.util.UUID;

public record GroupCreateDto(String name, String description, UUID userId) {
}
