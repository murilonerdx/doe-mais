package com.murilonerdx.doemais.dto;

import com.murilonerdx.doemais.entities.Order;
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

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }

}
