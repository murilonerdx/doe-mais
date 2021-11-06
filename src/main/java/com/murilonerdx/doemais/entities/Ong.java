package com.murilonerdx.doemais.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Ong{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cnpj;
    private String endereco;

    @OneToMany(fetch=FetchType.EAGER)
    private List<OrderItem> orderItem;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Userman user;
}
