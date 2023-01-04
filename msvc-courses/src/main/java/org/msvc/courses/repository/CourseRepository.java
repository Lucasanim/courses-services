package org.msvc.courses.repository;

import org.msvc.courses.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
}
