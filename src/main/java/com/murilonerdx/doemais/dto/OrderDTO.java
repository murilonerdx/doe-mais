package com.murilonerdx.doemais.dto;

import com.murilonerdx.doemais.entities.OrdemItem;
import com.murilonerdx.doemais.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Instant moment;
    private OrderStatus status;
    List<ProductDTO> products = new ArrayList<>();

    public OrderDTO(OrdemItem entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        products = entity.getProducts().stream().map(ProductDTO::new).collect(Collectors.toList());
    }

}
