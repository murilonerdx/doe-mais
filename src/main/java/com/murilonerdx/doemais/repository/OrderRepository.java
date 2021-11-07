package com.murilonerdx.doemais.repository;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByOng(Ong ong);
}
