package com.msvc.users.services;

import com.msvc.users.client.CourseClientRest;
import com.msvc.users.models.entities.User;
import com.msvc.users.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseClientRest courseClient;

    public UserServiceImpl(UserRepository userRepository, CourseClientRest courseClient) {
        this.userRepository = userRepository;
        this.courseClient = courseClient;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public List<User> getUsersByIds(Iterable<Long> ids) {
        return (List<User>) this.userRepository.findAllById(ids);
    }

    @Override
    @Transactional
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        this.userRepository.deleteById(id);
        this.courseClient.deleteCourseUser(id);
    }

}
