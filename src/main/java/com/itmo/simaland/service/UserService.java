package com.itmo.simaland.service;


import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.enums.Role;
import com.itmo.simaland.model.enums.Status;
import com.itmo.simaland.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserById(Long id) {
        return findUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public User createUser(User user) {
        user.setRole(Role.CUSTOMER);
        user.setStatus(Status.Active);
        return userRepository.save(user);
    }

    public User updateUserRole(Long userId, Role newRole) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        user.setRole(newRole);
        return userRepository.save(user);
    }
}
