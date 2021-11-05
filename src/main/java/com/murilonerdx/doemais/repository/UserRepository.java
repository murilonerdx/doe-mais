package com.murilonerdx.doemais.repository;

import com.murilonerdx.doemais.entities.Userman;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Userman, Long> {
    Optional<Userman> findByUsername(String username);
}
