package com.itmo.simaland.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "role_privilege")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RolePrivilege {

    private Long id;

    @Column("role_id")
    private Long role;

    @Column("role_id")
    private Long privilege;

}