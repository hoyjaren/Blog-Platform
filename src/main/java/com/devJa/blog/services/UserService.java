package com.devJa.blog.services;

import com.devJa.blog.Domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
