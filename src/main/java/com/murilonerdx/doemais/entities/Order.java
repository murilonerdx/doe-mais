package com.murilonerdx.doemais.entities;

import com.murilonerdx.doemais.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TB_ORDER_ITEM")
public class Order {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime moment;


    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name ="tb_order_product", joinColumns = @JoinColumn(name="order_id"), inverseJoinColumns = @JoinColumn(name="product_id"))
    private Set<Product> products = new HashSet<>();

    @OneToOne()
    private Ong ong;

    public Order(Long id, LocalDateTime moment) {
        this.id = id;
        this.moment = moment;
    }
}
