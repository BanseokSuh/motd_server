package com.lightcc.motd.domain.user.infrastructure.entity;

import com.lightcc.motd.domain.user.domain.UserRole;
import jakarta.persistence.*;

@Entity
public class UserJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false)
    private String loginId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private UserRole role;

//    private Timestamp createdAt;
//    private Timestamp updatedAt;
//    private Timestamp deletedAt;
}

