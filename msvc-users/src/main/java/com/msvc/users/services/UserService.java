package com.msvc.users.services;

import com.msvc.users.models.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    User save(User user);
    void delete(Long id);

    Optional<User> assignUserToCourse(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> removeUserFromCourse(User user, Long courseId);
}
