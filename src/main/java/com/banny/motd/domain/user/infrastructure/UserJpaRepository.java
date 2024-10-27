package com.banny.motd.domain.user.infrastructure;

import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLoginId(String loginId);

}

