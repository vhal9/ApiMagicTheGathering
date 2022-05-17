package com.zappts.MagicTheGathering.controller;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.IdsOfCardsDTO;
import com.zappts.MagicTheGathering.domain.dto.PackCreationDTO;
import com.zappts.MagicTheGathering.domain.dto.PackDTO;
import com.zappts.MagicTheGathering.exception.*;
import com.zappts.MagicTheGathering.service.PackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/packs")
@RequiredArgsConstructor
public class PackController {

    private final PackService packService;

    @GetMapping
    public ResponseEntity<List<PackDTO>> listAllPacks(
            @RequestParam(value = "order_field", required = false) String orderField) {
        return new ResponseEntity<>(packService.listAllPack(orderField), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackDTO> findById(
            @PathVariable("id")  Long id,
            @RequestParam(value = "order_field", required = false) String orderField
    ) throws PackNotFoundException {
        return new ResponseEntity<>(packService.findPackById(orderField, id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PackDTO> createPack(@RequestBody @Valid PackCreationDTO packCreationDTO) throws SomeCardsNotFoundException {
        return new ResponseEntity<>(packService.createPack(packCreationDTO), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/add")
    public ResponseEntity<PackDTO> addCard(@PathVariable("id") Long idPack, @RequestBody @Valid CardDTO cardDTO) throws PackNotFoundException, ForbiddenException {
        return new ResponseEntity<>(packService.addCardToPack(idPack, cardDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}/addCards")
    public ResponseEntity<PackDTO> addCard(@PathVariable("id") Long idPack, @RequestBody @Valid IdsOfCardsDTO idsOfCardsDTO) throws PackNotFoundException, ForbiddenException, SomeCardsNotFoundException {
        return new ResponseEntity<>(packService.addCardsToPack(idPack, idsOfCardsDTO), HttpStatus.OK);
    }

    @PatchMapping("/{id}/remove/{idCard}")
    public ResponseEntity<PackDTO> removeCard(@PathVariable("id") Long idPack, @PathVariable Long idCard)
            throws PackNotFoundException, RemoveNonExistentCardException, CardNotFoundException, ForbiddenException {
        return new ResponseEntity<>(packService.removeCardToPack(idPack, idCard), HttpStatus.OK);
    }

}

