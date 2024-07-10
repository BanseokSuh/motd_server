package com.lightcc.motd.domain.user.domain.repository;

import com.lightcc.motd.domain.user.infrastructure.UserRepositoryCustom;
import com.lightcc.motd.domain.user.infrastructure.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {
}
