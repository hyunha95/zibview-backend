package com.view.zib.domain.user.repository;

import com.view.zib.domain.user.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"location"})
    Optional<User> findByEmail(String email);

    Optional<User> findBySubject(String subject);
}
