package com.murilonerdx.doemais.dto;

import com.murilonerdx.doemais.entities.Userman;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CNPJ;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OngDTO {
    private String name;
    @CNPJ
    private String cnpj;
    private String endereco;
    private UserDTO user;
}
