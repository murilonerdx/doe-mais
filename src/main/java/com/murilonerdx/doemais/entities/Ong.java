package com.murilonerdx.doemais.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@PrimaryKeyJoinColumn(name = "id_usuario")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TB_ONG")
public class Ong extends Userman{

	private static final long serialVersionUID = 1L;

	@Column(unique=true)
	@NotBlank
    private String name;
	
	@Column(unique=true)
	@NotBlank
    private String cnpj;
	
	@NotBlank
	private String endereco;
}