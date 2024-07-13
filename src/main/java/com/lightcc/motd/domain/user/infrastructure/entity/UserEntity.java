package com.lightcc.motd.domain.user.infrastructure.entity;

import com.lightcc.motd.domain.user.domain.User;
import com.lightcc.motd.domain.user.domain.UserRole;
import com.lightcc.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"user\"", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_login_id", columnNames = "login_id")
})
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true)
    private String loginId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static UserEntity from(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginId(user.getLoginId());
        userEntity.setUserName(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        return userEntity;
    }

    public User toDomain() {
        User user = new User();
        user.setId(id);
        user.setLoginId(loginId);
        user.setUserName(userName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}

