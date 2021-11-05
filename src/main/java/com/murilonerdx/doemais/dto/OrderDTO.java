package com.murilonerdx.doemais.dto;

import com.murilonerdx.doemais.entities.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private Instant moment;

    public OrderDTO(OrderItem entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }

}
