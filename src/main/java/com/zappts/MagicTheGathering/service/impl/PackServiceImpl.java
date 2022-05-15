package com.zappts.MagicTheGathering.service.impl;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.domain.entity.Card;
import com.zappts.MagicTheGathering.domain.entity.Pack;
import com.zappts.MagicTheGathering.domain.mapper.CardMapper;
import com.zappts.MagicTheGathering.domain.mapper.PackDTOMapper;
import com.zappts.MagicTheGathering.domain.mapper.PackMapper;
import com.zappts.MagicTheGathering.exception.PackNotFoundException;
import com.zappts.MagicTheGathering.exception.RemoveNonExistentCardException;
import com.zappts.MagicTheGathering.repository.PackRepository;
import com.zappts.MagicTheGathering.service.CardService;
import com.zappts.MagicTheGathering.service.PackService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackServiceImpl implements PackService {

    private final PackRepository packRepository;
    private final PackDTOMapper packDTOMapper;
    private final PackMapper packMapper;
    private final CardMapper cardMapper;
    private final CardService cardService;


    @Override
    public List<PackDTO> listAllPack(String orderField) {
        List<Pack> packList = packRepository.findAll();
        packList.forEach(pack -> orderCardsByNameOrPrice(pack, orderField));
        return packList.stream().map(packDTOMapper::execute)
                .collect(Collectors.toList());
    }

    @Override
    public PackDTO findPackById(String orderField, Long id) throws PackNotFoundException {
        Pack pack = getPackbyId(id);
        if (Objects.nonNull(orderField))
            orderCardsByNameOrPrice(pack, orderField);
        return packDTOMapper.execute(getPackbyId(id));
    }

    @Override
    public PackDTO createPack(PackDTO packDTO) {
        Pack pack = packMapper.execute(packDTO);
        return packDTOMapper.execute(packRepository.save(pack));
    }

    @Override
    @Transactional
    public PackDTO addCardToPack(Long idPack, CardDTO cardDTO) throws PackNotFoundException {

        Pack pack = getPackbyId(idPack);
        pack.addCard(createOrGetCardByCardDTO(cardDTO));
        return packDTOMapper.execute(packRepository.save(pack));
    }

    @Override
    public PackDTO removeCardToPack(Long idPack, Long idCard) throws RemoveNonExistentCardException, PackNotFoundException {

        Pack pack = getPackbyId(idPack);

        if (!packHasCard(pack, idCard))
            throw new RemoveNonExistentCardException();

        removeCardWithId(pack, idCard);
        return packDTOMapper.execute(packRepository.save(pack));
    }

    public Pack getPackbyId(Long id) throws PackNotFoundException {
        Optional<Pack> packOptional = packRepository.findById(id);
        if (packOptional.isEmpty()) {
            throw new PackNotFoundException(id);
        }
        return packOptional.get();
    }

    public Boolean isNewCard(Long id) {
        return !(Objects.nonNull(id) && cardService.verifyIfCardExists(id));
    }

    public Boolean packHasCard(Pack pack, Long idCard) {
        return pack.getCards().stream().anyMatch(
                card -> Objects.equals(card.getId(), idCard)
        );
    }

    private Card createOrGetCardByCardDTO(CardDTO cardDTO) {
        if (isNewCard(cardDTO.getId())) {
            return cardService.createCard(cardDTO);
        } else {
            return cardMapper.execute(cardDTO);
        }
    }
    private void removeCardWithId(Pack pack, Long idCard) throws RemoveNonExistentCardException {
        Optional<Card> cardToRemove = pack.getCards().stream().filter(
                card -> Objects.equals(card.getId(), idCard)).findFirst();
        if (cardToRemove.isEmpty())
            throw new RemoveNonExistentCardException();
        pack.getCards().remove(cardToRemove.get());
    }

    private void orderCardsByNameOrPrice(Pack pack, String orderField) {
        if (Objects.equals(orderField, "name")) {
            pack.setCards(pack.getCards().stream()
                    .sorted(Comparator.comparing(Card::getName))
                    .collect(Collectors.toList())
            );
        } else if (Objects.equals(orderField, "price")) {
            pack.setCards(pack.getCards().stream()
                    .sorted(Comparator.comparing(Card::getPrice))
                    .collect(Collectors.toList())
            );
        }
    }


}
