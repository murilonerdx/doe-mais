package com.murilonerdx.doemais.entities;

import com.murilonerdx.doemais.entities.enums.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TB_PRODUTO")
public class Product {
	
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    private String imageUri;
    private LocalDateTime dueDate;
    private ProductStatus status;
    
    @OneToOne
    private Business business;
}