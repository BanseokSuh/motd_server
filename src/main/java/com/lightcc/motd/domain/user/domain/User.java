package com.lightcc.motd.domain.user.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class User {

    /*
     * data
     */
    private Long id;
    private String loginId;
    private String userName;
    private String email;
    private String password;
    private UserRole role;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    /*
     * action
     */
}
