package com.aman.AirBnb.AirBnb.Entities;

import com.aman.AirBnb.AirBnb.Enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String name;

    @ElementCollection(fetch = FetchType.EAGER) //JPA will create new table
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

}
