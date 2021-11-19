package com.murilonerdx.doemais.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOrderByNameDesc();

    @Query("select p from Product p where p.business.id = ?1")
	List<Product> findAMyProducts(Long businessId);
    
    @Query("select p from Product p where p.order = ?1")
    Product findByOrder(Order order);
}
