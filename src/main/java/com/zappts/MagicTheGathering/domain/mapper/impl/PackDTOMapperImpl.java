package com.zappts.MagicTheGathering.domain.mapper.impl;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.Pack;
import com.zappts.MagicTheGathering.domain.mapper.CardDTOMapper;
import com.zappts.MagicTheGathering.domain.mapper.PackDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PackDTOMapperImpl implements PackDTOMapper {

    private final CardDTOMapper cardDTOMapper;

    @Override
    public PackDTO execute(Pack pack) {
        return PackDTO.builder()
                .id(pack.getId())
                .cardsDTO(getListOfCardsDTO(pack.getCards()))
                .build();
    }

    private List<CardDTO> getListOfCardsDTO(List<Card> cardList) {
        return cardList.stream().map(cardDTOMapper::execute)
                .collect(Collectors.toList());
    }
}
