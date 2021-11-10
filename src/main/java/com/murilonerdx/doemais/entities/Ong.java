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
@Table(name="TB_ONG")
public class Ong{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cnpj;
    private String address;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Userman user;
}
