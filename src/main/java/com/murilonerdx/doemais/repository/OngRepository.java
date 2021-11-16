package com.murilonerdx.doemais.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Userman;

public interface OngRepository extends JpaRepository<Ong, Long> {
    Ong findByUser(Userman user);
    
	public Ong findByName(String username);
}
