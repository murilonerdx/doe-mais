package com.murilonerdx.doemais.dto;

import com.murilonerdx.doemais.entities.OrderItem;
import com.murilonerdx.doemais.entities.Userman;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Instant moment;

    public OrderDTO(OrderItem entity) {
        moment = entity.getMoment();
    }

}
