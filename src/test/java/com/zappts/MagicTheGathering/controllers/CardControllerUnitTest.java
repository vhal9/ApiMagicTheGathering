package com.zappts.MagicTheGathering.controllers;

import com.zappts.MagicTheGathering.builders.CardDTOBuilder;
import com.zappts.MagicTheGathering.controller.CardController;
import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.NumberOfCardsSameTypeDTO;
import com.zappts.MagicTheGathering.domain.dto.PriceOfCardDTO;
import com.zappts.MagicTheGathering.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.math.BigDecimal;
import java.util.Collections;

import static com.zappts.MagicTheGathering.utils.JsonConvertionUtils.asJsonString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CardControllerUnitTest {
    private static final String CARD_API_URL_PATH = "/api/cards/";
    private static final Long VALID_ID = 1L;
    private static final Long INVALID_ID = 2L;
    private static final String CHANGE_PRICE_PATH = "/updatePrice/";
    private static final String CHANGE_NUMBER_OF_CARDS_SAME_TYPE_PATH = "/updateNumberOfSameType/";

    private MockMvc mockMvc;

    @Mock
    private CardService cardService;

    @InjectMocks
    private CardController cardController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void itShouldReturnSuccess_WhenListAll() throws Exception {

        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();

        when(cardService.listAll()).thenReturn(Collections.singletonList(cardDTO));

        mockMvc.perform(get(CARD_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
        verify(cardService, times(1)).listAll();

    }

    @Test
    void itShouldReturnSuccess_WhenListAllWithEmptyList() throws Exception {

        when(cardService.listAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get(CARD_API_URL_PATH).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(cardService, times(1)).listAll();

    }

    @Test
    void itShouldReturnSuccess_WhenCreateCard() throws Exception {

        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();

        when(cardService.createCard(cardDTO)).thenReturn(cardDTO);

        mockMvc.perform(post(CARD_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cardDTO)))
                .andExpect(status().isCreated());
        verify(cardService, times(1)).createCard(any(CardDTO.class));
    }

    @Test
    void itShouldReturnBadRequest_WhenCreateCardWithInvalidCard() throws Exception {

        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();
        cardDTO.setPrice(BigDecimal.valueOf(-22.4));

        mockMvc.perform(post(CARD_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cardDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldReturnSuccess_WhenChangePrice() throws Exception {

        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();
        PriceOfCardDTO priceOfCardDTO = new PriceOfCardDTO();
        priceOfCardDTO.setPrice(BigDecimal.valueOf(22.4));

        when(cardService.changePrice(VALID_ID, priceOfCardDTO))
                .thenReturn(cardDTO);

        mockMvc.perform(patch(CARD_API_URL_PATH + VALID_ID + CHANGE_PRICE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(priceOfCardDTO)))
                .andExpect(status().isOk());
        verify(cardService, times(1))
                .changePrice(any(Long.class), any(PriceOfCardDTO.class));

    }

    @Test
    void itShouldReturnBadRequest_WhenChangePriceWithInvalidPrice() throws Exception {

        PriceOfCardDTO priceOfCardDTO = new PriceOfCardDTO();
        priceOfCardDTO.setPrice(BigDecimal.valueOf(-22.3));

        mockMvc.perform(patch(CARD_API_URL_PATH + VALID_ID + CHANGE_PRICE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(priceOfCardDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldReturnSuccess_WhenChangeNumberOfSameType() throws Exception {

        CardDTO cardDTO = CardDTOBuilder.builder().build().toCardDTO();
        NumberOfCardsSameTypeDTO numberOfCardsSameTypeDTO = new NumberOfCardsSameTypeDTO();
        numberOfCardsSameTypeDTO.setNumberOfSameType(4);

        when(cardService.changeNumberOfSameType(VALID_ID, numberOfCardsSameTypeDTO))
                .thenReturn(cardDTO);

        mockMvc.perform(patch(CARD_API_URL_PATH + VALID_ID + CHANGE_NUMBER_OF_CARDS_SAME_TYPE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(numberOfCardsSameTypeDTO)))
                .andExpect(status().isOk());
        verify(cardService, times(1))
                .changeNumberOfSameType(any(Long.class), any(NumberOfCardsSameTypeDTO.class));

    }

    @Test
    void itShouldReturnBadRequest_WhenChangeNumberOfSameTypeWithInvalidNumber() throws Exception {

        NumberOfCardsSameTypeDTO numberOfCardsSameTypeDTO = new NumberOfCardsSameTypeDTO();
        numberOfCardsSameTypeDTO.setNumberOfSameType(-1);

        mockMvc.perform(patch(CARD_API_URL_PATH + VALID_ID + CHANGE_NUMBER_OF_CARDS_SAME_TYPE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(numberOfCardsSameTypeDTO)))
                .andExpect(status().isBadRequest());
    }


}
