package com.zappts.MagicTheGathering.domain.dto;

import com.zappts.MagicTheGathering.domain.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {

    private Long id;

    @NotBlank(message = "Campo nome não pode ser vazio.")
    @Size(min = 4, max = 40, message = "Campo nome deve conter de 4 a 40 caracteres.")
    private String name;

    @NotBlank(message = "Campo edição não pode ser vazio.")
    @Size(min = 4, max = 15, message = "Campo edição deve conter de 4 a 15 caracteres.")
    private String edition;

    private Language language;

    @NotNull(message = "Campo laminado não pode ser vazio.")
    private Boolean isFoil;

    @NotNull(message = "Campo preço não pode ser nulo.")
    @Min(value = 0, message = "Campo preço deve ser maior ou igual a 0.")
    private BigDecimal price;

    @NotNull(message = "Campo do número de cartas com essa característica não pode ser nulo")
    @Min(value = 0, message = "Campo do número de cartas com essa característica deve ser maior ou igual a 0.")
    private Integer numberOfCardsOfTheSameType;

    private Long idUsuario;

}
