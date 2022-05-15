package com.zappts.MagicTheGathering.domain.mapper;

import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.domain.entity.Pack;

public interface PackDTOMapper {
    PackDTO execute(Pack pack);
}
