package com.toy.blog.domain.repository;

import com.toy.blog.domain.common.Status;
import com.toy.blog.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , com.toy.blog.domain.repository.custom.UserRepositoryCustom {

    Optional<User> findByIdAndStatus(Long id, Status.User status);

    boolean existsByIdAndStatus(Long id, Status.User status);

    Optional<User> findByEmail(@NotNull String email);

}
