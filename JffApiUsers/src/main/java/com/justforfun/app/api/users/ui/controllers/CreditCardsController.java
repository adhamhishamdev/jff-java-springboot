package com.justforfun.app.api.users.ui.controllers;

import com.justforfun.app.api.users.mapper.CustomMapper;
import com.justforfun.app.api.users.service.creditcard.CreditCardsService;
import com.justforfun.app.api.users.shared.CreditCardDTO;
import com.justforfun.app.api.users.ui.model.creditcard.CreateCreditCardRequestModel;
import com.justforfun.app.api.users.ui.model.creditcard.CreateCreditCardResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/credit-cards")
public class CreditCardsController {

    @Autowired
    private Environment env;

    @Autowired
    CreditCardsService creditCardsService;

    @Autowired
    CustomMapper customMapper;

    @GetMapping("/status/check")
    public String status() {
        return "Get status API users credit cards is working on port " + env.getProperty("local.server.port");
    }

    // create a card
    @PostMapping("/create/card")
    public ResponseEntity<CreateCreditCardResponseModel> createCreditCard(@RequestBody CreateCreditCardRequestModel creditCardDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        CreditCardDTO creditCardDTO = modelMapper.map(creditCardDetails, CreditCardDTO.class);
        CreditCardDTO createdCreditCard = creditCardsService.createCreditCard(creditCardDTO);
        CreateCreditCardResponseModel returnValue = modelMapper.map(createdCreditCard, CreateCreditCardResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    // create a card via manual mapper
    @PostMapping("/create/card/v2")
    public ResponseEntity<CreateCreditCardResponseModel> createCreditCardV2(@RequestBody CreateCreditCardRequestModel creditCardDetails) {
        CreditCardDTO creditCardDTO = customMapper.toCreditCardDTO(creditCardDetails);
        CreditCardDTO createdCreditCard = creditCardsService.createCreditCardV2(creditCardDTO);
        CreateCreditCardResponseModel cardResponseModel = customMapper.toCardResponseModel(createdCreditCard);
        return ResponseEntity.status(HttpStatus.CREATED).body(cardResponseModel);
    }

    // generate cards by number input
    @PostMapping("/generate/card/{numberOfCards}")
    public ResponseEntity<List<CreateCreditCardResponseModel>> generateCards(@PathVariable int numberOfCards) {
        // list for generated cards based on CreateCreditCardResponseModel
        List<CreateCreditCardResponseModel> generatedCards = new ArrayList<>();
        // list of created random cards
        List<CreditCardDTO> createdRandomCards = creditCardsService.createRandomCard(numberOfCards);
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (int i = 0; i < numberOfCards; i++) {
            CreateCreditCardResponseModel returnValue = modelMapper.map(createdRandomCards.get(i), CreateCreditCardResponseModel.class);
            generatedCards.add(returnValue);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedCards);
    }

    // generate cards by number input via manual mapping
    @PostMapping("/generate/card/v2/{numberOfCards}")
    public ResponseEntity<List<CreateCreditCardResponseModel>> generateCardsV2(@PathVariable int numberOfCards) {
        // list for generated cards based on CreateCreditCardResponseModel
        List<CreateCreditCardResponseModel> generatedCards = new ArrayList<>();
        // list of created random cards
        List<CreditCardDTO> createdRandomCards = creditCardsService.createRandomCardV2(numberOfCards);
        for (int i = 0; i < numberOfCards; i++) {
            CreateCreditCardResponseModel returnValue = customMapper.toCardResponseModel(createdRandomCards.get(i));
            generatedCards.add(returnValue);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedCards);
    }

    // delete credit cards in the database based on number input
    @DeleteMapping("/delete/card/rows/{numberOfCards}")
    public ResponseEntity<Void> deleteCardsByAmount(@PathVariable int numberOfCards) {
        creditCardsService.deleteCardsByAmount(numberOfCards);
        return ResponseEntity.noContent().build();
    }
}
