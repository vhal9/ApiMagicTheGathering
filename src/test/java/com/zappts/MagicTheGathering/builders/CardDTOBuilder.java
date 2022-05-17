package com.zappts.MagicTheGathering.builders;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.enums.Language;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class CardDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Antecipate";

    @Builder.Default
    private String edition = "ediçao 1";

    @Builder.Default
    private Language language = Language.ENGLISH;

    @Builder.Default
    private Boolean isFoil = true;

    @Builder.Default
    private BigDecimal price = BigDecimal.valueOf(44.2);

    @Builder.Default
    private Integer numberOfCardsOfTheSameType = 1;

    @Builder.Default
    private Long idUser = 1L;

    public CardDTO toCardDTO() {
        return new CardDTO(id,
                name,
                edition,
                language,
                isFoil,
                price,
                numberOfCardsOfTheSameType,
                idUser);
    }
}
