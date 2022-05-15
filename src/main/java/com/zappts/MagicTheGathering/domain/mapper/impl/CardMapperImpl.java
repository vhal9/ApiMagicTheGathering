package com.zappts.MagicTheGathering.domain.mapper.impl;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.mapper.CardMapper;
import org.springframework.stereotype.Component;

@Component
public class CardMapperImpl implements CardMapper {
    @Override
    public Card execute(CardDTO cardDTO) {
        return Card.builder()
                .id(cardDTO.getId())
                .name(cardDTO.getName())
                .edition(cardDTO.getEdition())
                .isFoil(cardDTO.getIsFoil())
                .language(cardDTO.getLanguage())
                .numberOfCardsOfTheSameType(cardDTO.getNumberOfCardsOfTheSameType())
                .price(cardDTO.getPrice())
                .build();
    }
}
