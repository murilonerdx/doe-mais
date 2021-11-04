package com.murilonerdx.doemais.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Ong {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cnpj;

    @OneToOne
    private User user;

    @ManyToOne(fetch=FetchType.EAGER)
    private OrdemItem ordemItem;
}
