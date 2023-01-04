package org.msvc.courses.service;

import org.msvc.courses.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> findAll();
    Optional<Course> getById(Long id);
    Course save(Course course);
    void delete(Long id);
}
