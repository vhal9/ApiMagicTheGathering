package com.zappts.MagicTheGathering.domain.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class NumberOfCardsSameTypeDTO {

    @NotNull(message = "Campo do número de cartas com essa característica não pode ser nulo")
    @Min(value = 0, message = "Campo do número de cartas com essa característica deve ser maior ou igual a 0.")
    private Integer numberOfSameType;
}
