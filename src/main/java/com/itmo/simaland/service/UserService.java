package com.itmo.simaland.service;


import com.itmo.simaland.model.entity.Privilege;
import com.itmo.simaland.model.entity.User;
import com.itmo.simaland.model.enums.Role;
import com.itmo.simaland.model.enums.Status;
import com.itmo.simaland.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

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
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUserRole(Long userId, Role newRole) {
        User user = getUserById(userId);
        user.setRole(newRole);
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
        System.out.println("Loading user by username: " + username);
        User customUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        System.out.println("Found user: " + customUser);
        Role roleEnum = customUser.getRole();

        Set<Privilege> privileges = roleMappingService.getPrivilegesForRoleEnum(roleEnum);

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Privilege privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege.getName()));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEnum.name()));

        return new org.springframework.security.core.userdetails.User(
                customUser.getUsername(),
                customUser.getPassword(),
                authorities);
    }
}
