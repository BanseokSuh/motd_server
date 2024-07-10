package com.lightcc.motd.domain.user.domain.repository;

import com.lightcc.motd.domain.user.infrastructure.UserRepositoryCustom;
import com.lightcc.motd.domain.user.infrastructure.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long>, UserRepositoryCustom {
}
