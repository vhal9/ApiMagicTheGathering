package com.zappts.MagicTheGathering.domain.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
public class PriceOfCardDTO {

    @NotNull(message = "Id não pode ser nulo.")
    private Long id;

    @NotNull(message = "Campo preço não pode ser nulo.")
    @Min(value = 0, message = "Campo preço deve ser maior ou igual a 0.")
    private BigDecimal price;


}
