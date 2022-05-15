package com.zappts.MagicTheGathering.domain.mapper.impl;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.mapper.CardDTOMapper;
import org.springframework.stereotype.Component;

@Component
public class CardDTOMapperImpl implements CardDTOMapper {
    @Override
    public CardDTO execute(Card card) {
        return CardDTO.builder()
                .id(card.getId())
                .name(card.getName())
                .edition(card.getEdition())
                .isFoil(card.getIsFoil())
                .language(card.getLanguage())
                .numberOfCardsOfTheSameType(card.getNumberOfCardsOfTheSameType())
                .price(card.getPrice())
                .build();
    }
}
