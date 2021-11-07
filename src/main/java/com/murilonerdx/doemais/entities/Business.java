package com.murilonerdx.doemais.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TB_BUSINESS")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String website;
    private String address;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Userman user;

    private double tribute;
    private double points;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TB_BUSINESS_PRODUCT", joinColumns = {
            @JoinColumn(name = "id_business")
    }, inverseJoinColumns = {
            @JoinColumn(name = "id_product")
    })
    private Set<Product> products = new HashSet<>();

}
