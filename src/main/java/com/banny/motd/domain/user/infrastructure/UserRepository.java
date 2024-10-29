package com.banny.motd.domain.user.infrastructure;

import com.banny.motd.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    User getById(Long id);

    Optional<User> findById(Long id);

    User getByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);

    User save(User user);

    void delete(User user);

    void deleteAllInBatch();

}
