package com.banny.motd.domain.user.infrastructure.entity;

import com.banny.motd.domain.user.domain.Gender;
import com.banny.motd.domain.user.domain.User;
import com.banny.motd.domain.user.domain.UserRole;
import com.banny.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE \"user\" SET deleted_at = NOW() where id = ?")
@Table(name = "\"user\"", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_login_id", columnNames = "login_id")
})
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String loginId;

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(50)")
    private String userName;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(20)")
    private String password;

    @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "userRole", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .loginId(user.getLoginId())
                .userName(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .gender(user.getGender())
                .build();
    }

    public User toDomain() {
        return User.builder()
                .id(id)
                .loginId(loginId)
                .userName(userName)
                .email(email)
                .password(password)
                .userRole(userRole)
                .gender(gender)
                .build();
    }
}

