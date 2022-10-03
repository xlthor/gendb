package de.amthor.gendb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amthor.gendb.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByLoginnameOrEmail(String loginname, String email);
    Optional<User> findByLoginname(String loginname);
    Boolean existsByLoginname(String loginname);
    Boolean existsByEmail(String email);
}
