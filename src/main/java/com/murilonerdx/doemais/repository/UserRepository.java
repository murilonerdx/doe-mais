package com.murilonerdx.doemais.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.murilonerdx.doemais.entities.Userman;

@Repository
public interface UserRepository extends JpaRepository<Userman, Long> {
    Optional<Userman> findByUsername(String username);
}
