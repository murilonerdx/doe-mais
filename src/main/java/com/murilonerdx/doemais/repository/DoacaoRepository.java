package com.murilonerdx.doemais.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.murilonerdx.doemais.entities.Doacao;

@Repository
public interface DoacaoRepository extends JpaRepository<Doacao, Long>{

}
