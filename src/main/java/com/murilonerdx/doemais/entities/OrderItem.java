package com.murilonerdx.doemais.entities;

import com.murilonerdx.doemais.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Instant moment;


    @ManyToMany
    @JoinTable(name ="tb_order_product", joinColumns = @JoinColumn(name="order_id"), inverseJoinColumns = @JoinColumn(name="product_id"))
    private Set<Product> products = new HashSet<>();

    public OrderItem(Long id, Instant moment, OrderStatus status) {
        this.id = id;
        this.moment = moment;
    }
}
