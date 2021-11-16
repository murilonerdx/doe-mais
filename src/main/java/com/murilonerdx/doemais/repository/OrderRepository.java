package com.murilonerdx.doemais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.murilonerdx.doemais.entities.Ong;
import com.murilonerdx.doemais.entities.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
	List<Order> findByOng(Ong ong);
	
		
	@Query("SELECT o FROM Order o WHERE o.product.id = ?1  AND o.id = ?2")
	public Order findOrder(Long productId, Long orderId );
}
