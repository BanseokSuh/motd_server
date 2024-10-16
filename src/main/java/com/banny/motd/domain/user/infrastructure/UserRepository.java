package com.banny.motd.domain.user.infrastructure;

import com.banny.motd.domain.user.infrastructure.eneity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, UserRepositoryCustom {

    Optional<UserEntity> findByLoginId(String loginId);

}
