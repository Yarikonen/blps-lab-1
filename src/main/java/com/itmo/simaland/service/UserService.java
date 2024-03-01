package com.itmo.simaland.service;


import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.repository.UserRepository;
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
}
