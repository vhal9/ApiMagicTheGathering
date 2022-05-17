package com.zappts.MagicTheGathering.builders;

import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import com.zappts.MagicTheGathering.domain.enums.Language;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class CardBuilder {
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
    private UserEntity user = new UserEntity();

    public Card toCard() {
        return new Card(id,
                name,
                edition,
                language,
                isFoil,
                price,
                numberOfCardsOfTheSameType,
                user);
    }
}
