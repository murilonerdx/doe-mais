package com.murilonerdx.doemais.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessDTO {
    private Long id;
    private String name;
    @Length(min=5, message="Digite um site valido")
    private String website;
    @Length(min=10, message="Digite um endereço com minimo 10 caracteres")
    private String address;
}
