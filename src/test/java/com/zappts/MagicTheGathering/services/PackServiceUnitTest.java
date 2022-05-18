package com.zappts.MagicTheGathering.services;

import com.zappts.MagicTheGathering.builders.*;
import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.IdsOfCardsDTO;
import com.zappts.MagicTheGathering.domain.dto.PackCreationDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.Pack;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import com.zappts.MagicTheGathering.domain.mapper.CardMapper;
import com.zappts.MagicTheGathering.domain.mapper.PackDTOMapper;
import com.zappts.MagicTheGathering.domain.mapper.PackMapper;
import com.zappts.MagicTheGathering.exception.ForbiddenException;
import com.zappts.MagicTheGathering.exception.PackNotFoundException;
import com.zappts.MagicTheGathering.exception.RemoveNonExistentCardException;
import com.zappts.MagicTheGathering.exception.SomeCardsNotFoundException;
import com.zappts.MagicTheGathering.repository.PackRepository;
import com.zappts.MagicTheGathering.service.CardService;
import com.zappts.MagicTheGathering.service.impl.PackServiceImpl;
import com.zappts.MagicTheGathering.service.impl.UserServiceImpl;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PackServiceUnitTest {

    @Mock
    private PackRepository packRepository;

    @Mock
    private PackMapper packMapper;

    @Mock
    private PackDTOMapper packDTOMapper;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CardService cardService;

    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private PackServiceImpl packService;

    @Test
    void itShouldReturnAListOfPackDTO_WhenListAllPack() {

        PackDTO packDTO = PackDTOBuilder.builder().build().toPackDTO();
        Pack pack = PackBuilder.builder().build().toPack();

        when(packRepository.findAll()).thenReturn(Collections.singletonList(pack));
        when(packDTOMapper.execute(pack)).thenReturn(packDTO);

        List<PackDTO> returnedCardDTOList = packService.listAllPack("");

        verify(packRepository, times(1)).findAll();
        verify(packDTOMapper, times(1)).execute(any(Pack.class));
        assertThat(returnedCardDTOList, is(not(empty())));
        assertThat(returnedCardDTOList.size(), is(equalTo(1)));
        assertThat(returnedCardDTOList.get(0), Is.is(equalTo(packDTO)));

    }

    @Test
    void itShouldReturnAPackDTO_WhenFindPackById() throws PackNotFoundException {

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();
        Pack pack = PackBuilder.builder().build().toPack();

        when(packRepository.findById(expectedPackDTO.getId())).thenReturn(Optional.of(pack));
        when(packDTOMapper.execute(any())).thenReturn(expectedPackDTO);

        PackDTO returnedPackDTO = packService.findPackById("", expectedPackDTO.getId());

        verify(packRepository, times(1)).findById(anyLong());
        verify(packDTOMapper, times(1)).execute(any());
        assertThat(expectedPackDTO, is(equalTo(returnedPackDTO)));
    }

    @Test
    void itShouldReturnAPackNotFoundException_WhenFindPackById() {

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();

        when(packRepository.findById(expectedPackDTO.getId())).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> packService.findPackById("", expectedPackDTO.getId()));

        verify(packRepository, times(1)).findById(anyLong());
    }


    @Test
    void itShouldReturnAPackDTO_WhenCreatePack() throws SomeCardsNotFoundException {

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();
        Pack pack = PackBuilder.builder().build().toPack();
        PackCreationDTO packCreationDTO = PackCreationDTOBuilder.builder().build().toPackCreationDTO();
        UserEntity user = UserEntityBuilder.builder().build().toUserEntity();


        when(userService.getLoggedUser()).thenReturn(user);
        when(packMapper.execute(packCreationDTO)).thenReturn(pack);
        when(cardService.getListOfCardsByListOfIds(anyList())).thenReturn(Collections.emptyList());
        when(packDTOMapper.execute(any())).thenReturn(expectedPackDTO);

        PackDTO returnedPackDTO = packService.createPack(packCreationDTO);

        verify(userService, times(1)).getLoggedUser();
        verify(packMapper, times(1)).execute(any(PackCreationDTO.class));
        verify(cardService, times(1)).getListOfCardsByListOfIds(anyList());
        verify(packDTOMapper, times(1)).execute(any());
        assertThat(expectedPackDTO, is(equalTo(returnedPackDTO)));
    }

    @Test
    void itShouldReturnAPackDTO_WhenAddCardToPack() throws ForbiddenException, PackNotFoundException {

        UserEntity user = UserEntityBuilder.builder().build().toUserEntity();

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();
        Pack pack = PackBuilder.builder().build().toPack();
        pack.setUser(user);
        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();
        Card card = CardBuilder.builder().build().toCard();

        when(packRepository.findById(expectedPackDTO.getId())).thenReturn(Optional.of(pack));
        doNothing().when(userService).verifyIfUserHasPermission(user);
        when(cardService.cardExists(cardDTO.getId())).thenReturn(Boolean.FALSE);
        when(cardService.createCard(cardDTO)).thenReturn(cardDTO);
        when(cardMapper.execute(cardDTO)).thenReturn(card);
        when(packRepository.save(pack)).thenReturn(pack);
        when(packDTOMapper.execute(any())).thenReturn(expectedPackDTO);

        PackDTO returnedPackDTO = packService.addCardToPack(pack.getId(), cardDTO);

        verify(packRepository,times(1)).findById(anyLong());
        verify(userService, times(1)).verifyIfUserHasPermission(any());
        verify(cardService, times(1)).cardExists(anyLong());
        verify(cardService, times(1)).createCard(any());
        verify(cardMapper, times(1)).execute(any());
        verify(packRepository, times(1)).save(any(Pack.class));
        verify(packDTOMapper, times(1)).execute(any());
        assertThat(expectedPackDTO, is(equalTo(returnedPackDTO)));
    }

    @Test
    void itShouldReturnPackNotFoundException_WhenAddCardToPack() {

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();
        Pack pack = PackBuilder.builder().build().toPack();
        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();

        when(packRepository.findById(expectedPackDTO.getId())).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> packService.addCardToPack(pack.getId(), cardDTO));

        verify(packRepository,times(1)).findById(anyLong());

    }

    @Test
    void itShouldReturnAPackDTO_WhenRemoveCardToPack() throws ForbiddenException, PackNotFoundException, RemoveNonExistentCardException {

        UserEntity user = UserEntityBuilder.builder().build().toUserEntity();

        Card card = CardBuilder.builder().build().toCard();
        List<Card> cardList = new ArrayList<>();
        cardList.add(card);

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();
        Pack pack = PackBuilder.builder().build().toPack();
        pack.setUser(user);
        pack.setCards(cardList);

        when(packRepository.findById(expectedPackDTO.getId())).thenReturn(Optional.of(pack));
        doNothing().when(userService).verifyIfUserHasPermission(user);
        when(packRepository.save(pack)).thenReturn(pack);
        when(packDTOMapper.execute(any())).thenReturn(expectedPackDTO);

        PackDTO returnedPackDTO = packService.removeCardToPack(pack.getId(), card.getId());

        verify(packRepository,times(1)).findById(anyLong());
        verify(userService, times(1)).verifyIfUserHasPermission(any());
        verify(packRepository, times(1)).save(any(Pack.class));
        verify(packDTOMapper, times(1)).execute(any());
        assertThat(expectedPackDTO, is(equalTo(returnedPackDTO)));
    }

    @Test
    void itShouldReturnPackNotFoundException_WhenRemoveCardToPack() {

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();
        Pack pack = PackBuilder.builder().build().toPack();
        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();

        when(packRepository.findById(expectedPackDTO.getId())).thenReturn(Optional.empty());

        assertThrows(PackNotFoundException.class,
                () -> packService.removeCardToPack(pack.getId(), cardDTO.getId()));

        verify(packRepository,times(1)).findById(anyLong());
    }

    @Test
    void itShouldReturnAPackDTO_WhenAddCardsToPack() throws ForbiddenException, PackNotFoundException, SomeCardsNotFoundException {
        UserEntity user = UserEntityBuilder.builder().build().toUserEntity();

        IdsOfCardsDTO idsToBeAddToPack = IdsOfCardsDTOBuilder.builder().build().toIdsOfCardsDTO();
        List<Card> cardListMock = new ArrayList<>();
        cardListMock.add(CardBuilder.builder().build().toCard());
        cardListMock.add(CardBuilder.builder().id(2L).build().toCard());

        List<CardDTO> expectedCardDTOList = new ArrayList<>();
        expectedCardDTOList.add(CardDTOBuilder.builder().build().toCardDTO());
        expectedCardDTOList.add(CardDTOBuilder.builder().id(2L).build().toCardDTO());

        PackDTO expectedPackDTO = PackDTOBuilder.builder().build().toPackDTO();
        expectedPackDTO.setCardsDTO(expectedCardDTOList);
        Pack pack = PackBuilder.builder().build().toPack();
        pack.setUser(user);

        when(packRepository.findById(expectedPackDTO.getId())).thenReturn(Optional.of(pack));
        doNothing().when(userService).verifyIfUserHasPermission(user);
        when(cardService.getListOfCardsByListOfIds(idsToBeAddToPack.getIdsCards())).thenReturn(cardListMock);
        when(packRepository.save(pack)).thenReturn(pack);
        when(packDTOMapper.execute(any())).thenReturn(expectedPackDTO);

        PackDTO returnedPackDTO = packService.addCardsToPack(pack.getId(), idsToBeAddToPack);

        verify(packRepository,times(1)).findById(anyLong());
        verify(userService, times(1)).verifyIfUserHasPermission(any());
        verify(cardService, times(1)).getListOfCardsByListOfIds(anyList());
        verify(packRepository, times(1)).save(any(Pack.class));
        verify(packDTOMapper, times(1)).execute(any());
        assertThat(expectedPackDTO, is(equalTo(returnedPackDTO)));
        assertThat(expectedPackDTO.getCardsDTO().size(), is(equalTo(returnedPackDTO.getCardsDTO().size())));

    }

}
