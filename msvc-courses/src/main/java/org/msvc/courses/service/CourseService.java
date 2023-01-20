package org.msvc.courses.service;

import org.msvc.courses.models.User;
import org.msvc.courses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> getById(Long id);
    Optional<Course> getByIdWithUsers(Long id, String token);
    Course save(Course course);
    void delete(Long id);
    void deleteCourseUserById(Long userId);

    Optional<User> assignUserToCourse(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> removeUserFromCourse(User user, Long courseId);
}
