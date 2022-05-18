package com.zappts.MagicTheGathering.builders;

import com.zappts.MagicTheGathering.domain.dto.PackCreationDTO;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class PackCreationDTOBuilder {

    @Builder.Default
    private String name = "Lendarios";

    @Builder.Default
    private List<Long> idsCards = new ArrayList<>();

    public PackCreationDTO toPackCreationDTO() {
        return new PackCreationDTO(name, idsCards);
    }
}
