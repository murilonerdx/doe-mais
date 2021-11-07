package com.murilonerdx.doemais.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@PrimaryKeyJoinColumn(name = "id_usuario")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TB_EMPRESA")
public class Business extends Userman {

	private static final long serialVersionUID = 1L;

	private String name;
    
    private String website;
    
    private String address;
    
    private String cnpj;

    private double pontos;

    
    public double getPontosMensais() {
    //  Somar todas as doacoes do mes atual e retornar a diferenca do dia do pedido e do dia da validade de cada produto
//    	Consultar as doacoes e somar os pontos
    	return 0.0;
    }
}