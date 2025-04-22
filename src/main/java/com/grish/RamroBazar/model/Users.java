package com.grish.RamroBazar.model;

import com.grish.RamroBazar.enums.RoleTypes;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleTypes role;
    private String email;
    private String phone;
}
