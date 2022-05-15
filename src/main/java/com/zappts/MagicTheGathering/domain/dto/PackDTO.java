package com.zappts.MagicTheGathering.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PackDTO {

    private Long id;

    private UserDTO userDTO;

    @Valid
    private List<CardDTO> cardsDTO = new ArrayList<>();
}
