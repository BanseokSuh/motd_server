package com.banny.motd.domain.user;

import com.banny.motd.global.enums.Device;
import com.banny.motd.global.exception.ApiStatusType;
import com.banny.motd.global.exception.ApplicationException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements UserDetails {

    private Long id;
    private String loginId;
    private String userName;
    private String nickName;
    private String email;
    @JsonIgnore
    private String password;
    private UserRole userRole;
    private UserStatus userStatus;
    private String profileImageUrl;
    @Setter
    private Device device;
    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;
    @JsonIgnore
    private LocalDateTime deletedAt;

    @Builder
    private User(Long id, String loginId, String userName, String nickName, String email, String password, UserRole userRole, UserStatus userStatus, String profileImageUrl, Device device, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.loginId = loginId;
        this.userName = userName;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.profileImageUrl = profileImageUrl;
        this.device = device;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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
