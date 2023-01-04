package org.msvc.courses.service;

import org.msvc.courses.clients.UserClientRest;
import org.msvc.courses.models.User;
import org.msvc.courses.models.entity.Course;
import org.msvc.courses.models.entity.CourseUser;
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

    @Autowired
    private UserClientRest userClient;

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

    @Override
    @Transactional
    public Optional<User> assignUserToCourse(User user, Long courseId) {
        Optional<Course> optionalCourse = this.courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            User userMsvc = userClient.getUser(user.getId());

            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.addCourseUser(courseUser);
            this.courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> optionalCourse = this.courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            User userMsvc = userClient.createUser(user);

            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.addCourseUser(courseUser);
            this.courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUserFromCourse(User user, Long courseId) {
        Optional<Course> optionalCourse = this.courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            User userMsvc = userClient.getUser(user.getId());

            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.removeCourseUser(courseUser);
            this.courseRepository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }
}
