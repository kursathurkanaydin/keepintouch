package com.hk.whatsapp.service;

import com.hk.whatsapp.dto.CreateUserRequest;
import com.hk.whatsapp.entity.Role;
import com.hk.whatsapp.entity.User;
import com.hk.whatsapp.exception.UserNotFoundException;
import com.hk.whatsapp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void userRegister(CreateUserRequest createUserRequest) {
        User user = new User();
        user.setEmail(createUserRequest.getEmail());
        user.setPassword(passwordEncoder.encode(createUserRequest.getPassword()));
        user.getRoles().add(Role.USER);
        user.setChats(new ArrayList<>());
        userRepository.save(user);
    }

    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return authentication;
    }

    public User getCurrentUser() {
        Authentication authentication = getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userRepository.findByEmail(authentication.getName()).orElse(null);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException(String.format("User not found with email: %s", email)));
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

}
