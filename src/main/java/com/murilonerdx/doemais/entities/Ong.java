package com.murilonerdx.doemais.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TB_ONG")
public class Ong{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;
    @CNPJ
    private String cnpj;
    @NotEmpty
    private String address;

    @OneToOne(cascade=CascadeType.PERSIST)
    private Userman user;
}
