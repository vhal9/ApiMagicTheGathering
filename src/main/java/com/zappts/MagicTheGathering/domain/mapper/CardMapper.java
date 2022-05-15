package com.zappts.MagicTheGathering.domain.mapper;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import org.springframework.stereotype.Component;

@Component
public interface CardMapper {
    Card execute(CardDTO cardDTO);
}
