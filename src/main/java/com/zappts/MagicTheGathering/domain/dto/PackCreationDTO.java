package com.zappts.MagicTheGathering.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class PackCreationDTO {

    @NotBlank(message = "Campo nome não pode ser vazio.")
    @Size(min = 4, max = 40, message = "Campo nome deve conter de 4 a 40 caracteres.")
    private String name;

    private List<Long> idCards;

}
