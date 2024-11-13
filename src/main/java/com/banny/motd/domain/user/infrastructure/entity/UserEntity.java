package com.banny.motd.domain.user.infrastructure.entity;

import com.banny.motd.domain.user.Gender;
import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.UserRole;
import com.banny.motd.domain.user.UserStatus;
import com.banny.motd.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@NoArgsConstructor
@SQLRestriction("deleted_at IS NULL AND user_status != 'DELETED'")
@SQLDelete(sql = "UPDATE \"user\" SET login_id = login_id || '_deleted_' || id, deleted_at = NOW(), user_status = 'DELETED' where id = ?")
@Table(name = "\"user\"",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_user_login_id", columnNames = "login_id")
        },
        indexes = {
                @Index(name = "index_user_deleted_at_user_status", columnList = "deleted_at, user_status")
        })
@Entity
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT")
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true, columnDefinition = "VARCHAR(100)")
    private String loginId;

    @Column(name = "user_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String userName;

    @Column(name = "nick_name", nullable = false, columnDefinition = "VARCHAR(100)")
    private String nickName;

    @Column(name = "email", nullable = false, columnDefinition = "VARCHAR(50)")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "VARCHAR(100)")
    private String password;

    @Column(name = "gender", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "userRole", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(name = "userStatus", nullable = false, columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "profile_image_url", nullable = true, columnDefinition = "VARCHAR(255)")
    private String profileImageUrl;

    @Builder
    private UserEntity(Long id, String loginId, String userName, String nickName, String email, String password, Gender gender, UserRole userRole, UserStatus userStatus, String profileImageUrl) {
        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.profileImageUrl = profileImageUrl;
    }

    public static UserEntity from(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .loginId(user.getLoginId())
                .userName(user.getUsername())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .password(user.getPassword())
                .userRole(user.getUserRole())
                .userStatus(user.getUserStatus())
                .profileImageUrl(user.getProfileImageUrl())
                .gender(user.getGender())
                .build();
    }

    public User toDomain() {
        return User.builder()
                .id(id)
                .loginId(loginId)
                .userName(userName)
                .nickName(nickName)
                .email(email)
                .password(password)
                .userRole(userRole)
                .userStatus(userStatus)
                .profileImageUrl(profileImageUrl)
                .gender(gender)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .deletedAt(getDeletedAt())
                .build();
    }

}

