package com.murilonerdx.doemais.services;

import com.murilonerdx.doemais.dto.OrderDTO;
import com.murilonerdx.doemais.entities.Order;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.repository.OrderRepository;
import com.murilonerdx.doemais.util.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public OrderDTO findById(Long id) {

        Order entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return DozerConverter.parseObject(entity, OrderDTO.class);
    }

    public void delete(Long id) {
        Order entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }
}
