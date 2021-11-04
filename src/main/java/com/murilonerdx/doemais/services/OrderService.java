package com.murilonerdx.doemais.services;

import com.murilonerdx.doemais.dto.OrderDTO;
import com.murilonerdx.doemais.dto.ProductDTO;
import com.murilonerdx.doemais.entities.OrdemItem;
import com.murilonerdx.doemais.entities.OrderStatus;
import com.murilonerdx.doemais.entities.Product;
import com.murilonerdx.doemais.repository.OrderRepository;
import com.murilonerdx.doemais.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDTO> findAll(){
        List<OrdemItem> list = repository.findOrdersWithProducts();
        return list.stream().map(OrderDTO::new).collect(Collectors.toList());
    }
    @Transactional
    public OrderDTO insert(OrderDTO dto){
        OrdemItem ordemItem = new OrdemItem(null, Instant.now(), OrderStatus.AVAILABLE);
        for(ProductDTO p: dto.getProducts()){
            Product product = productRepository.getOne(p.getId());
            ordemItem.getProducts().add(product);
        }
        ordemItem = repository.save(ordemItem);
        return new OrderDTO(ordemItem);
    }

    @Transactional
    public OrderDTO setDelivered(Long id){
        OrdemItem ordemItem = repository.getOne(id);
        ordemItem.setStatus(OrderStatus.UNAVAILABLE);
        ordemItem = repository.save(ordemItem);
        return new OrderDTO(ordemItem);
    }
}
