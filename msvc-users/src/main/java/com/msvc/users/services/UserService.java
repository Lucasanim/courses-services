package com.msvc.users.services;

import com.msvc.users.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> getUsersByIds(Iterable<Long> ids);
    User save(User user);
    void delete(Long id);
}
