package com.zappts.MagicTheGathering.services;

import com.zappts.MagicTheGathering.builders.CardBuilder;
import com.zappts.MagicTheGathering.builders.CardDTOBuilder;
import com.zappts.MagicTheGathering.builders.UserEntityBuilder;
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
import com.zappts.MagicTheGathering.service.impl.CardServiceImpl;
import com.zappts.MagicTheGathering.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CardServiceUnitTest {

    @Mock
    private CardRespository cardRespository;

    @Mock
    private CardMapper cardMapper;

    @Mock
    private CardDTOMapper cardDTOMapper;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void itShouldReturnAListOfCardDTO_WhenListAll() {

        Card cardMock = CardBuilder.builder().build().toCard();
        CardDTO expectedCardDTO = CardDTOBuilder.builder().build().toCardDTO();

        when(cardRespository.findAll()).thenReturn(Collections.singletonList(cardMock));
        when(cardDTOMapper.execute(cardMock)).thenReturn(expectedCardDTO);

        List<CardDTO> returnedListOfCardDTO = cardService.listAll();

        verify(cardRespository, times(1)).findAll();
        verify(cardDTOMapper, times(1)).execute(any(Card.class));
        assertThat(returnedListOfCardDTO, is(not(empty())));
        assertThat(returnedListOfCardDTO.size(), is(equalTo(1)));
        assertThat(returnedListOfCardDTO.get(0), is(equalTo(expectedCardDTO)));

    }

    @Test
    void itShouldReturnACard_WhenCreateCard(){

        CardDTO cardDTOMock = CardDTOBuilder.builder().build().toCardDTO();
        Card expectedCard = CardBuilder.builder().build().toCard();
        UserEntity user = UserEntityBuilder.builder().build().toUserEntity();
        expectedCard.setUser(user);

        when(userService.getLoggedUser()).thenReturn(user);
        when(cardMapper.execute(cardDTOMock)).thenReturn(expectedCard);
        when(cardRespository.save(expectedCard)).thenReturn(expectedCard);

        Card returnedCard = cardService.createCard(cardDTOMock);

        verify(userService, times(1)).getLoggedUser();
        verify(cardMapper, times(1)).execute(any(CardDTO.class));
        verify(cardRespository, times(1)).save(any(Card.class));
        assertThat(returnedCard, is(equalTo(expectedCard)));

    }

    @Test
    void itShouldReturnUsernameNotFoundException_WhenCreateCardWithInvalidUser(){

        CardDTO cardDTOMock = CardDTOBuilder.builder().build().toCardDTO();

        when(userService.getLoggedUser()).thenThrow(UsernameNotFoundException.class);

        assertThrows(UsernameNotFoundException.class, () -> cardService.createCard(cardDTOMock));

    }

    @Test
    void itShouldReturnCardDTO_WhenChangePrice() throws ForbiddenException, CardNotFoundException {

        PriceOfCardDTO priceOfCardDTO = new PriceOfCardDTO();
        priceOfCardDTO.setPrice(BigDecimal.valueOf(22.5));

        Card cardMock = CardBuilder.builder().build().toCard();

        Card expectedCard = CardBuilder.builder().build().toCard();
        expectedCard.setPrice(priceOfCardDTO.getPrice());

        CardDTO expectedCardDTO = CardDTOBuilder.builder().build().toCardDTO();
        expectedCardDTO.setPrice(priceOfCardDTO.getPrice());

        doNothing().when(userService).verifyIfUserHasPermission(any(UserEntity.class));
        when(cardRespository.findById(cardMock.getId())).thenReturn(Optional.of(cardMock));
        when(cardRespository.save(cardMock)).thenReturn(expectedCard);
        when(cardDTOMapper.execute(expectedCard)).thenReturn(expectedCardDTO);

        CardDTO returnedCardDTO = cardService.changePrice(cardMock.getId(), priceOfCardDTO);

        verify(userService, times(1)).verifyIfUserHasPermission(any(UserEntity.class));
        verify(cardRespository, times(1)).findById(any(Long.class));
        verify(cardRespository, times(1)).save(any(Card.class));
        verify(cardDTOMapper, times(1)).execute(any(Card.class));
        assertThat(returnedCardDTO, is(equalTo(expectedCardDTO)));

    }

    @Test
    void itShouldReturnCardNotFoundException_WhenChangePriceWithInvalidCard() {

        PriceOfCardDTO priceOfCardDTO = new PriceOfCardDTO();
        Card cardMock = CardBuilder.builder().build().toCard();

        when(cardRespository.findById(cardMock.getId())).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> cardService.changePrice(cardMock.getId(), priceOfCardDTO));

    }

    @Test
    void itShouldReturnForbiddenException_WhenChangePriceWithInvalidUser() throws ForbiddenException {
        PriceOfCardDTO priceOfCardDTO = new PriceOfCardDTO();
        Card cardMock = CardBuilder.builder().build().toCard();

        when(cardRespository.findById(cardMock.getId())).thenReturn(Optional.of(cardMock));
        doThrow(ForbiddenException.class).when(userService).verifyIfUserHasPermission(any());

        assertThrows(ForbiddenException.class,
                () -> cardService.changePrice(cardMock.getId(), priceOfCardDTO));
        verify(cardRespository, times(1)).findById(any(Long.class));
    }


    @Test
    void itShouldReturnCardDTO_WhenChangeNumberOfSameType() throws ForbiddenException, CardNotFoundException {
        NumberOfSameTypeOfCardDTO numberOfSameTypeOfCardDTO = new NumberOfSameTypeOfCardDTO();
        numberOfSameTypeOfCardDTO.setNumberOfSameType(10);
        Card cardMock = CardBuilder.builder().build().toCard();

        Card expectedCard = CardBuilder.builder().build().toCard();
        expectedCard.setNumberOfCardsOfTheSameType(numberOfSameTypeOfCardDTO.getNumberOfSameType());

        CardDTO expectedCardDTO = CardDTOBuilder.builder().build().toCardDTO();
        expectedCardDTO.setNumberOfCardsOfTheSameType(numberOfSameTypeOfCardDTO.getNumberOfSameType());

        doNothing().when(userService).verifyIfUserHasPermission(any(UserEntity.class));
        when(cardRespository.findById(cardMock.getId())).thenReturn(Optional.of(cardMock));
        when(cardRespository.save(cardMock)).thenReturn(expectedCard);
        when(cardDTOMapper.execute(expectedCard)).thenReturn(expectedCardDTO);

        CardDTO returnedCardDTO = cardService.changeNumberOfSameType(cardMock.getId(), numberOfSameTypeOfCardDTO);

        verify(userService, times(1)).verifyIfUserHasPermission(any(UserEntity.class));
        verify(cardRespository, times(1)).findById(any(Long.class));
        verify(cardRespository, times(1)).save(any(Card.class));
        verify(cardDTOMapper, times(1)).execute(any(Card.class));
        assertThat(returnedCardDTO, is(equalTo(expectedCardDTO)));
    }

    @Test
    void itShouldReturnCardNotFoundException_WhenChangeNumberOfSameTypeWithInvalidCard(){
        NumberOfSameTypeOfCardDTO numberOfSameTypeOfCardDTO = new NumberOfSameTypeOfCardDTO();
        Card cardMock = CardBuilder.builder().build().toCard();

        when(cardRespository.findById(cardMock.getId())).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class,
                () -> cardService.changeNumberOfSameType(cardMock.getId(), numberOfSameTypeOfCardDTO));
    }

    @Test
    void itShouldReturnForbiddenException_WhenChangeNumberOfSameTypeWithInvalidUser() throws ForbiddenException {
        NumberOfSameTypeOfCardDTO numberOfSameTypeOfCardDTO = new NumberOfSameTypeOfCardDTO();
        Card cardMock = CardBuilder.builder().build().toCard();

        when(cardRespository.findById(cardMock.getId())).thenReturn(Optional.of(cardMock));
        doThrow(ForbiddenException.class).when(userService).verifyIfUserHasPermission(any());

        assertThrows(ForbiddenException.class,
                () -> cardService.changeNumberOfSameType(cardMock.getId(), numberOfSameTypeOfCardDTO));
        verify(cardRespository, times(1)).findById(any(Long.class));
    }

    @Test
    void itShouldReturnTrue_WhenVerifyIfCardExistsWithExistingCard() {
        Card cardMock = CardBuilder.builder().build().toCard();
        when(cardRespository.findById(any(Long.class))).thenReturn(Optional.of(cardMock));

        assertThat(Boolean.TRUE, is(equalTo(cardService.verifyIfCardExists(any(Long.class)))));
        verify(cardRespository, times(1)).findById(any(Long.class));

    }

    @Test
    void itShouldReturnFalse_WhenVerifyIfCardExistsWithNonExistingCard() {

        when(cardRespository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThat(Boolean.FALSE, is(equalTo(cardService.verifyIfCardExists(any(Long.class)))));
        verify(cardRespository, times(1)).findById(any(Long.class));
    }

    @Test
    void itShouldReturnCard_WhenGetCardByIdWithExistingCard() throws CardNotFoundException {
        Card cardMock = CardBuilder.builder().build().toCard();

        when(cardRespository.findById(any(Long.class))).thenReturn(Optional.of(cardMock));

        Card returnedCard = cardService.getCardById(cardMock.getId());
        verify(cardRespository, times(1)).findById(any(Long.class));
        assertThat(returnedCard, is(equalTo(cardMock)));
    }

    @Test
    void itShouldReturnCardNotFoundException_WhenGetCardByIdWithNonExistingCard() {

        when(cardRespository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(CardNotFoundException.class, () -> cardService.getCardById(any(Long.class)));
        verify(cardRespository, times(1)).findById(any(Long.class));

    }


}
