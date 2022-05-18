package com.zappts.MagicTheGathering.builders;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class PackDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Lendarios";

    @Builder.Default
    private List<CardDTO> cardsDTO = new ArrayList<>();

    @Builder.Default
    private Long idUser = 1L;

    public PackDTO toPackDTO() {
        return new PackDTO(id,
                name,
                idUser,
                cardsDTO);
    }
}
