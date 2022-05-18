package com.zappts.MagicTheGathering.builders;

import com.zappts.MagicTheGathering.domain.dto.IdsOfCardsDTO;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Builder
public class IdsOfCardsDTOBuilder {
    @Builder.Default
    private List<Long> idsCards = new ArrayList<>(Arrays.asList(1L,2L));

    public IdsOfCardsDTO toIdsOfCardsDTO(){
        return new IdsOfCardsDTO(idsCards);
    }
}
