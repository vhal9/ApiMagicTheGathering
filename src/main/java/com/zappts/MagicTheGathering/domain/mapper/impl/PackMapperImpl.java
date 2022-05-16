package com.zappts.MagicTheGathering.domain.mapper.impl;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.Pack;
import com.zappts.MagicTheGathering.domain.mapper.CardMapper;
import com.zappts.MagicTheGathering.domain.mapper.PackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PackMapperImpl implements PackMapper {

    private final CardMapper cardMapper;

    @Override
    public Pack execute(PackDTO packDTO) {
        return Pack.builder()
                .id(packDTO.getId())
                .name(packDTO.getName())
                .cards(getListOfCards(packDTO.getCardsDTO()))
                .build();
    }

    private List<Card> getListOfCards(List<CardDTO> cardDTOList) {
        return cardDTOList.stream().map(cardMapper::execute)
                .collect(Collectors.toList());
    }
}
