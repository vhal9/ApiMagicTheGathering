package com.zappts.MagicTheGathering.controller;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.NumberOfCardsSameTypeDTO;
import com.zappts.MagicTheGathering.domain.dto.PriceOfCardDTO;
import com.zappts.MagicTheGathering.exception.CardNotFoundException;
import com.zappts.MagicTheGathering.exception.ForbiddenException;
import com.zappts.MagicTheGathering.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<CardDTO>> listAll() {
        return new ResponseEntity<>(cardService.listAll(), HttpStatus.OK);
    }

    @PatchMapping("{id}/updatePrice")
    public ResponseEntity<CardDTO> changePrice(@PathVariable("id") Long id, @RequestBody @Valid PriceOfCardDTO priceOfCardDTO) throws CardNotFoundException, ForbiddenException {
        return new ResponseEntity<>(cardService.changePrice(id, priceOfCardDTO), HttpStatus.OK);
    }

    @PatchMapping("{id}/updateNumberOfSameType")
    public ResponseEntity<CardDTO> changeNumberOfSameType(@PathVariable("id") Long id, @RequestBody @Valid NumberOfCardsSameTypeDTO numberOfCardsSameTypeDTO)
            throws CardNotFoundException, ForbiddenException {

        return new ResponseEntity<>(cardService.changeNumberOfSameType(id, numberOfCardsSameTypeDTO),
                HttpStatus.OK);
    }
}
