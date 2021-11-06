package com.murilonerdx.doemais.entities;

import com.murilonerdx.doemais.entities.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TB_PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUri;
    private LocalDateTime dueDate;
    private OrderStatus status;

    @OneToOne
    private Business business;
}
