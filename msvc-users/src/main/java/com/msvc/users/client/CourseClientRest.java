package com.msvc.users.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "172.17.0.1:8002")
public interface CourseClientRest {

    @DeleteMapping("/delete-user/{userId}")
    void deleteCourseUser(@PathVariable("userId") Long userId);

}
