package com.zappts.MagicTheGathering.controller;

import com.zappts.MagicTheGathering.domain.dto.CardDTO;
import com.zappts.MagicTheGathering.domain.dto.NumberOfSameTypeOfCardDTO;
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

    @PatchMapping("/updatePrice")
    public ResponseEntity<CardDTO> changePrice(@RequestBody @Valid PriceOfCardDTO priceOfCardDTO) throws CardNotFoundException, ForbiddenException {
        return new ResponseEntity<>(cardService.changePrice(priceOfCardDTO), HttpStatus.OK);
    }

    @PatchMapping("/updateNumberOfSameType")
    public ResponseEntity<CardDTO> changeNumberOfSameType(@RequestBody @Valid NumberOfSameTypeOfCardDTO numberOfSameTypeOfCardDTO)
            throws CardNotFoundException, ForbiddenException {

        return new ResponseEntity<>(cardService.changeNumberOfSameType(numberOfSameTypeOfCardDTO),
                HttpStatus.OK);
    }
}
