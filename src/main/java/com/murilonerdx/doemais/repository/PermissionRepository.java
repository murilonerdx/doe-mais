package com.murilonerdx.doemais.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilonerdx.doemais.entities.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long>{

	Optional<Permission> findByDescription(String description);

}
