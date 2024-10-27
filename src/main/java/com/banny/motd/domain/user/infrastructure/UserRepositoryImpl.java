package com.banny.motd.domain.user.infrastructure;

import com.banny.motd.domain.user.User;
import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import com.banny.motd.global.dto.response.ApiResponseStatusType;
import com.banny.motd.global.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User getById(Long id) {
        return findById(id).orElseThrow(() ->
                new ApplicationException(ApiResponseStatusType.FAIL_USER_NOT_FOUND, String.format("User %d is not found", id)));
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public User getByLoginId(String loginId) {
        return findByLoginId(loginId).orElseThrow(() ->
                new ApplicationException(ApiResponseStatusType.FAIL_USER_NOT_FOUND, String.format("User %s is not found", loginId)));
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return userJpaRepository.findByLoginId(loginId).map(UserEntity::toDomain);
    }

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.from(user)).toDomain();
    }

    @Override
    public void delete(User user) {
        userJpaRepository.delete(UserEntity.from(user));
    }

}
