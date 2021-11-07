package com.murilonerdx.doemais.entities;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TB_DOACAO")
public class Doacao {

	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
	
	private Instant momento;
	
	@OneToOne
	private Business business;
	
	@OneToOne
	private Order order;
	
	//Calculo a partir do dia e a quantidade de produto aceito atraves da difereanca ate o dia da validade.
	private int pontos;	
}