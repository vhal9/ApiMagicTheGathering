package com.zappts.MagicTheGathering.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackDTO {

    private Long id;

    @NotBlank(message = "Campo nome não pode ser vazio.")
    @Size(min = 4, max = 40, message = "Campo nome deve conter de 4 a 40 caracteres.")
    private String name;

    private Long idUsuario;

    @Valid
    private List<CardDTO> cardsDTO = new ArrayList<>();
}
