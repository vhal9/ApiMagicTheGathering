package com.zappts.MagicTheGathering.service;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.NumberOfSameTypeOfCardDTO;
import com.zappts.MagicTheGathering.domain.dto.PriceOfCardDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.exception.CardNotFoundException;
import com.zappts.MagicTheGathering.exception.ForbiddenException;

import java.util.List;

public interface CardService {

    List<CardDTO> listAll();

    Card createCard(CardDTO cardDTO);

    CardDTO changePrice(PriceOfCardDTO priceOfCardDTO) throws CardNotFoundException, ForbiddenException;

    CardDTO changeNumberOfSameType(NumberOfSameTypeOfCardDTO numberOfSameTypeOfCardDTO) throws CardNotFoundException, ForbiddenException;

    Boolean verifyIfCardExists(Long id);

    Card getCardById(Long id) throws CardNotFoundException;
}
