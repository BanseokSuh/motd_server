package com.banny.motd.domain.user;

import com.banny.motd.global.enums.Device;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

    private Long id;
    private String loginId;
    private String userName;
    private String nickName;
    private String email;
    @JsonIgnore
    private String password;
    private Gender gender;
    private UserRole userRole;
    private UserStatus userStatus;
    private String profileImageUrl;
    @Setter
    private Device device;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public User(Long id, String loginId, String userName, String nickName, String email, String password, Gender gender, UserRole userRole, UserStatus userStatus, String profileImageUrl) {
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

    /**
     * 비밀번호 일치 여부 확인
     *
     * @param password 비밀번호
     * @param encoder  BCryptPasswordEncoder
     */
    public void checkPasswordMatch(String password, BCryptPasswordEncoder encoder) {
        if (!encoder.matches(password, this.password)) {
            throw new ApplicationException(ApiStatusType.FAIL_USER_PASSWORD_MISMATCH, "Password does not match");
        }
    }

    /**
     * 사용자 status 검증
     */
    public void checkUserStatus() {
        if (this.userStatus != UserStatus.ACTIVE) {
            throw new ApplicationException(ApiStatusType.FAIL_USER_NOT_ACTIVE, "User status is not active");
        }
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.userRole.toString()));
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return this.userName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

}
