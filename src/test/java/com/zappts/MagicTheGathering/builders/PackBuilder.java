package com.zappts.MagicTheGathering.builders;

import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.Pack;
import com.zappts.MagicTheGathering.domain.entity.UserEntity;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class PackBuilder {
    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String name = "Lendarios";

    @Builder.Default
    private List<Card> cards = new ArrayList<>();

    @Builder.Default
    private UserEntity user = null;

    public Pack toPack() {
        return new Pack(id,
                name,
                user,
                cards);
    }
}
