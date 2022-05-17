package com.zappts.MagicTheGathering.service;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.PackCreationDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.exception.*;

import java.util.List;

public interface PackService {

    List<PackDTO> listAllPack(String orderField);
    PackDTO findPackById(String orderField, Long id) throws PackNotFoundException;
    PackDTO createPack(PackCreationDTO packCreationDTO) throws SomeCardsNotFoundException;
    PackDTO addCardToPack(Long idPack, CardDTO cardDTO) throws PackNotFoundException, ForbiddenException;
    PackDTO removeCardToPack(Long idPack, Long idCard) throws RemoveNonExistentCardException, PackNotFoundException, CardNotFoundException, ForbiddenException;
}
