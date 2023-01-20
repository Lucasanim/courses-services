package org.msvc.auth.service;

import org.msvc.auth.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private WebClient.Builder webClient;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = webClient
                    .build()
                    .get()
                    .uri("http://msvc-users/login", uriBuilder -> uriBuilder.queryParam("email", username).build())
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(User.class)
                    .block();
            logger.info("User login: " + user.getEmail());

            return new org.springframework.security.core.userdetails.User(username, user.getPassword(), true, true, true, true, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        } catch (RuntimeException e) {
            logger.error("Error retrieving user with email: " + username);
            throw new UsernameNotFoundException("User not found");
        }
    }
}
