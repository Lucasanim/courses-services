package org.msvc.courses.clients;

import org.msvc.courses.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-users", url = "localhost:8001")
public interface UserClientRest {

    @GetMapping("{userId}")
    public User getUser(@PathVariable Long userId);

    @PostMapping
    public User createUser(@RequestBody User user);

}
