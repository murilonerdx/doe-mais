package com.murilonerdx.doemais.dto;

import com.murilonerdx.doemais.entities.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime moment;

    ProductDTO product;

    public OrderDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }

}


