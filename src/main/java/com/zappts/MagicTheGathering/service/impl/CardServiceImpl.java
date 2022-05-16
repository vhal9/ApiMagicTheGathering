package com.zappts.MagicTheGathering.service.impl;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.NumberOfSameTypeOfCardDTO;
import com.zappts.MagicTheGathering.domain.dto.PriceOfCardDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import com.zappts.MagicTheGathering.domain.mapper.CardDTOMapper;
import com.zappts.MagicTheGathering.domain.mapper.CardMapper;
import com.zappts.MagicTheGathering.exception.CardNotFoundException;
import com.zappts.MagicTheGathering.exception.ForbiddenException;
import com.zappts.MagicTheGathering.repository.CardRespository;
import com.zappts.MagicTheGathering.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRespository cardRespository;
    private final CardMapper cardMapper;
    private final CardDTOMapper cardDTOMapper;
    private final UserServiceImpl userService;

    @Override
    public List<CardDTO> listAll() {
        return cardRespository.findAll().stream().map(cardDTOMapper::execute)
                .collect(Collectors.toList());
    }

    @Override
    public Card createCard(CardDTO cardDTO) {
        UserEntity user = userService.getLoggedUser();
        Card cardToSave = cardMapper.execute(cardDTO);
        cardToSave.setUser(user);
        return cardRespository.save(cardToSave);
    }

    @Override
    public CardDTO changePrice(PriceOfCardDTO priceOfCardDTO) throws CardNotFoundException, ForbiddenException {

        Card card = getCardById(priceOfCardDTO.getId());
        verifyIfUserHasPermission(card);

        card.setPrice(priceOfCardDTO.getPrice());
        return cardDTOMapper.execute(cardRespository.save(card));
    }

    @Override
    public CardDTO changeNumberOfSameType(NumberOfSameTypeOfCardDTO numberOfSameTypeOfCardDTO)
            throws CardNotFoundException, ForbiddenException {
        Card card = getCardById(numberOfSameTypeOfCardDTO.getId());
        verifyIfUserHasPermission(card);

        card.setNumberOfCardsOfTheSameType(numberOfSameTypeOfCardDTO.getNumberOfSameType());
        return cardDTOMapper.execute(cardRespository.save(card));
    }

    @Override
    public Boolean verifyIfCardExists(Long id) {
        Optional<Card> cardOptional = cardRespository.findById(id);
        return cardOptional.isPresent();
    }

    @Override
    public Card getCardById(Long id) throws CardNotFoundException {
        Optional<Card> cardOptional = cardRespository.findById(id);
        if (cardOptional.isEmpty()) {
            throw new CardNotFoundException(id);
        }
        return cardOptional.get();
    }

    public void verifyIfUserHasPermission(Card card) throws ForbiddenException {
        userService.verifyIfUserHasPermission(card.getUser());
    }


}
