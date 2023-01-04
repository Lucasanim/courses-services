package org.msvc.courses.service;

import org.msvc.courses.entity.Course;
import org.msvc.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) this.courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getById(Long id) {
        return this.courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return this.courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.courseRepository.deleteById(id);
    }
}
