package org.msvc.courses.controller;

import feign.FeignException;
import org.msvc.courses.models.User;
import org.msvc.courses.models.entity.Course;
import org.msvc.courses.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<?> createCourse(@Valid @RequestBody Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.getValidationErrorResponse(bindingResult);
        }
        Course createdCourse = this.courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course, BindingResult bindingResult, @PathVariable("courseId") Long courseId) {
        if (bindingResult.hasErrors()) {
            return this.getValidationErrorResponse(bindingResult);
        }
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

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUserToCourse(@RequestBody User user, @PathVariable("courseId") Long courseId) {
        Optional<User> optionalUser;
        try {
            optionalUser = this.courseService.assignUserToCourse(user, courseId);
        } catch (FeignException.FeignClientException e) {
            return ResponseEntity.badRequest().build();
        }
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUserOnCourse(@RequestBody User user, @PathVariable("courseId") Long courseId) {
        Optional<User> optionalUser;
        try {
            optionalUser = this.courseService.createUser(user, courseId);
        } catch (FeignException.FeignClientException e) {
            return ResponseEntity.badRequest().build();
        }
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{courseId}")
    public ResponseEntity<?> deleteUserOfCourse(@RequestBody User user, @PathVariable("courseId") Long courseId) {
        Optional<User> optionalUser;
        try {
            optionalUser = this.courseService.removeUserFromCourse(user, courseId);
        } catch (FeignException.FeignClientException e) {
            return ResponseEntity.badRequest().build();
        }
        if (optionalUser.isPresent()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> getValidationErrorResponse(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
