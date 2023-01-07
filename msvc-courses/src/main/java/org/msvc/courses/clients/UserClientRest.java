package org.msvc.courses.clients;

import org.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-users", url = "msvc-users:8001")
public interface UserClientRest {

    @GetMapping("{userId}")
    public User getUser(@PathVariable Long userId);

    @PostMapping
    public User createUser(@RequestBody User user);

    @GetMapping("/users-by-id")
    public List<User> getUsersByIds(@RequestParam Iterable<Long> usersIds);

}
