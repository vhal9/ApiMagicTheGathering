package com.zappts.MagicTheGathering.domain.mapper;

import com.zappts.MagicTheGathering.domain.dto.PackCreationDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.domain.entity.Pack;

public interface PackMapper {
    Pack execute(PackDTO packDTO);
    Pack execute(PackCreationDTO packCreationDTO);
}
