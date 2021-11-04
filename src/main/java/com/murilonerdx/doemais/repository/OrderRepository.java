package com.murilonerdx.doemais.repository;

import com.murilonerdx.doemais.entities.OrdemItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrdemItem, Long> {
    @Query("SELECT DISTINCT obj FROM OrdemItem obj JOIN FETCH obj.products WHERE obj.status = 0 ORDER BY obj.moment ASC")
    List<OrdemItem> findOrdersWithProducts();
}
