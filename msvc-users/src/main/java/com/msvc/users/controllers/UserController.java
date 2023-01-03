package com.msvc.users.controllers;

import com.msvc.users.models.entities.User;
import com.msvc.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Long id) {
        Optional<User> optionalUser = this.userService.findById(id);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = this.userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("userId") Long userId) {
        Optional<User> optionalUser = this.userService.findById(userId);
        if (optionalUser.isPresent()) {
            User queriedUser = optionalUser.get();
            queriedUser.setEmail(user.getEmail());
            queriedUser.setName(user.getName());
            queriedUser.setPassword(user.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.save(queriedUser));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") Long userId) {
        Optional<User> optionalUser = this.userService.findById(userId);
        if (optionalUser.isPresent()) {
            this.userService.delete(userId);
           return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
