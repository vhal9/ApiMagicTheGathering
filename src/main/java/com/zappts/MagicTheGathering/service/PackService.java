package com.zappts.MagicTheGathering.service;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.exception.CardNotFoundException;
import com.zappts.MagicTheGathering.exception.PackNotFoundException;
import com.zappts.MagicTheGathering.exception.RemoveNonExistentCardException;

import java.util.List;

public interface PackService {

    List<PackDTO> listAllPack(String orderField);
    PackDTO findPackById(String orderField, Long id) throws PackNotFoundException;
    PackDTO createPack(PackDTO packDTO);
    PackDTO addCardToPack(Long idPack, CardDTO cardDTO) throws PackNotFoundException;
    PackDTO removeCardToPack(Long idPack, Long idCard) throws RemoveNonExistentCardException, PackNotFoundException, CardNotFoundException;
}
