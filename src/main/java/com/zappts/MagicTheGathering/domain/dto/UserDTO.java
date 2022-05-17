package com.zappts.MagicTheGathering.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotEmpty(message = "Nome não pode ser vazio.")
    @Size(min = 4, max = 15, message = "Nome deve conter de 4 a 15 caracteres.")
    private String username;

    @NotEmpty(message = "Senha não pode ser vazia.")
    @Size(min = 8, max = 15, message = "Senha deve conter de 8 a 15 caracteres.")
    private String password;

}
