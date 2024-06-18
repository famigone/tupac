package com.example.application.services;

import com.example.application.model.Role;
import com.example.application.model.User;
import com.example.application.repository.UserRepository;
import com.example.application.security.AuthenticatedUser;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.Endpoint;

import java.util.Optional;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Endpoint
@AnonymousAllowed
public class UserEndpoint {
    private final PasswordEncoder passwordEncoder = null;
    private final UserRepository userRepository = null;
    public record UserRecord(
            Long id,
            String username,
            String name,
            String password,
            Set<Role> roles
            ) {
    }

    @Autowired
    private AuthenticatedUser authenticatedUser;

    public Optional<User> getAuthenticatedUser() {
        return authenticatedUser.get();
    }

    
    public User registerUser(@RequestBody User user) {
        user.setHashedPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
