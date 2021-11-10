package com.murilonerdx.doemais.repository;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOng(Ong ong);
}
