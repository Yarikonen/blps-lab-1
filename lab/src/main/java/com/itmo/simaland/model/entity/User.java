package com.itmo.simaland.model.entity;


import com.itmo.simaland.model.enums.RoleEnum;
import com.itmo.simaland.model.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="role")
    @Enumerated(value = EnumType.STRING)
    private RoleEnum roleEnum = RoleEnum.CUSTOMER;

    @Column(name="status")
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Override
    public String toString() {
        return "{" + "id=" + id + ", username='" + username + '\'' + ", password='" + password + '\'' + ", role=" + roleEnum + ", status=" + status + '}';
    }
}
