package com.murilonerdx.doemais.repository;

import com.murilonerdx.doemais.entities.OrdemItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OngRepository extends JpaRepository<OrdemItem, Long> {
}
