package com.murilonerdx.doemais.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.murilonerdx.doemais.entities.Business;

public interface BusinessRepository extends JpaRepository<Business, Long> {

	@Query("SELECT b FROM Business b WHERE b.user.id = ?1")
	public Business findUserId(Long userid);
}
