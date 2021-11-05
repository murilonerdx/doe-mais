package com.murilonerdx.doemais.services;

import com.murilonerdx.doemais.dto.OrderDTO;
import com.murilonerdx.doemais.entities.Business;
import com.murilonerdx.doemais.entities.OrderItem;
import com.murilonerdx.doemais.entities.enums.OrderStatus;
import com.murilonerdx.doemais.exceptions.ResourceNotFoundException;
import com.murilonerdx.doemais.repository.BusinessRepository;
import com.murilonerdx.doemais.repository.OrderRepository;
import com.murilonerdx.doemais.util.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private BusinessRepository businessRepository;

    public List<OrderItem> findAll(){
        return repository.findAll();
    }

    public OrderItem create(Long productId, Long businessId) {
        OrderItem orderItem = new OrderItem();
        Business business = businessRepository.findById(businessId).orElseThrow(() -> new ResourceNotFoundException("Empresa não encontada"));
        business.getProducts().stream().map(x -> {
            if (x.getId().equals(productId)) {
                x.setStatus(OrderStatus.UNAVAILABLE);
                orderItem.getProducts().add(x);
                return x;
            }
            return x;
        });

        if(orderItem.getProducts().isEmpty()) return null;
        orderItem.setMoment(Instant.now());
        businessRepository.save(business);
        return repository.save(orderItem);
    }

    public OrderDTO findById(Long id) {

        OrderItem entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        return DozerConverter.parseObject(entity, OrderDTO.class);
    }


    public void delete(Long id) {
        OrderItem entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
        repository.delete(entity);
    }
}
