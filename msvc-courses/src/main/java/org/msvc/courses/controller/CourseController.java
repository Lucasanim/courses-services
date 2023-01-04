package org.msvc.courses.controller;

import org.msvc.courses.entity.Course;
import org.msvc.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(this.courseService.findAll());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long courseId) {
        Optional<Course> optionalCourse = this.courseService.getById(courseId);
        return optionalCourse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = this.courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathVariable("courseId") Long courseId, @RequestBody Course course) {
        Optional<Course> optionalCourse = this.courseService.getById(courseId);
        if (optionalCourse.isPresent()) {
            Course queriedCourse = optionalCourse.get();
            queriedCourse.setName(course.getName());
            return ResponseEntity.ok(this.courseService.save(queriedCourse));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") Long courseId) {
        Optional<Course> optionalCourse = this.courseService.getById(courseId);
        if (optionalCourse.isPresent()) {
            this.courseService.delete(courseId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
