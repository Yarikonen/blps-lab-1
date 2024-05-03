package com.itmo.simaland.service;


import com.itmo.simaland.model.entity.Privilege;
import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.enums.RoleEnum;
import com.itmo.simaland.model.enums.Status;
import com.itmo.simaland.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final RoleMappingService roleMappingService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User getUserById(Long id) {
        return findUserById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public User createUser(User user) {
        logger.info("User trying to register with password: {}", user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Encoded password: {}", passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUserRole(Long userId, RoleEnum newRoleEnum) {
        User user = getUserById(userId);
        user.setRoleEnum(newRoleEnum);
        return userRepository.save(user);
    }

    public User updateUserStatus(Long userId, Status newStatus) {
        User user = getUserById(userId);
        user.setStatus(newStatus);
        return userRepository.save(user);
    }

    public User updateUsername(Long id, String username) {
        User user = getUserById(id);
        user.setUsername(username);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Loading user by username: {}", username);
        User customUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        logger.info("Found user: {}", customUser);
        RoleEnum roleEnum = customUser.getRoleEnum();

        Set<Privilege> privileges = roleMappingService.getPrivilegesForRoleEnum(roleEnum);
        String privilegeNames = privileges.stream()
                .map(Privilege::getName)
                .collect(Collectors.joining(", "));

        logger.info("User privileges: {}", privilegeNames);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEnum.name()));

        logger.info("User role: {}", roleEnum.name());
        return new org.springframework.security.core.userdetails.User(
                customUser.getUsername(),
                customUser.getPassword(),
                authorities);
    }
}
