package com.murilonerdx.doemais.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngDTO {
    private Long id;
    private String name;
    @CNPJ
    private String cnpj;
}
