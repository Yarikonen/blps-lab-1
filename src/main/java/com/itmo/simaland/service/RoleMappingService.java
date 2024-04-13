package com.itmo.simaland.service;

import com.itmo.simaland.model.entity.Privilege;
import com.itmo.simaland.model.enums.Role;
import com.itmo.simaland.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RoleMappingService {

    private final RoleRepository roleRepository;

    public Set<Privilege> getPrivilegesForRoleEnum(Role roleEnum) {
        com.itmo.simaland.model.entity.Role roleEntity = roleRepository.findByName(roleEnum.name())
                .orElseThrow(() -> new IllegalArgumentException("Role not found: " + roleEnum.name()));
        return roleEntity.getPrivileges();
    }
}

