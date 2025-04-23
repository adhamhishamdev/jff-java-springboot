package com.justforfun.app.api.users.ui.controllers;

import com.justforfun.app.api.users.mapper.CustomMapper;
import com.justforfun.app.api.users.service.transaction.TransactionsService;
import com.justforfun.app.api.users.shared.TransactionDTO;
import com.justforfun.app.api.users.shared.UserTransactionDetailsDTO;
import com.justforfun.app.api.users.ui.model.transaction.CreateTransactionRequestModel;
import com.justforfun.app.api.users.ui.model.transaction.CreateTransactionResponseModel;
import com.justforfun.app.api.users.ui.model.transaction.TransactionDetailsResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    @Autowired
    private Environment env;

    @Autowired
    TransactionsService transactionsService;

    @Autowired
    CustomMapper customMapper;

    Logger logger = LoggerFactory.getLogger(TransactionsController.class);

    @GetMapping("/status/check")
    public String status() {
        return "Get status API users transactions is working on port " + env.getProperty("local.server.port");
    }

    // to create a transaction
    @PostMapping("/create/transaction")
    public ResponseEntity<CreateTransactionResponseModel> createTransaction(@RequestBody CreateTransactionRequestModel transactionDetails) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TransactionDTO transactionDTO = modelMapper.map(transactionDetails, TransactionDTO.class);
        TransactionDTO createdTransaction = transactionsService.createTransaction(transactionDTO);
        CreateTransactionResponseModel returnValue = modelMapper.map(createdTransaction, CreateTransactionResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }

    // to create a transaction via manual mapping
    @PostMapping("/v2")
    public ResponseEntity<CreateTransactionResponseModel> createTransactionV2(@RequestBody CreateTransactionRequestModel transactionDetails) {
        TransactionDTO transactionDTO = customMapper.toTransactionDTO(transactionDetails);
        TransactionDTO createdTransaction = transactionsService.createTransactionV2(transactionDTO);
        CreateTransactionResponseModel transactionResponseModel = customMapper.toTransactionResponseModel(createdTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponseModel);
    }

    // generate transactions by number input
    @PostMapping("/generate/transaction/{numberOfTransactions}")
    public ResponseEntity<List<CreateTransactionResponseModel>> generateTransactions(@PathVariable int numberOfTransactions) {
        // list for generated transactions via CreateTransactionResponseModel
        List<CreateTransactionResponseModel> generatedTransactions = new ArrayList<>();
        // create the random transaction list, get the time taken of creation
        long start = System.currentTimeMillis();
        List<TransactionDTO> createdRandomTransactions = transactionsService.createRandomTransaction(numberOfTransactions);
        long executionTime = System.currentTimeMillis() - start;
        logger.info("Generate transactions (with model mapper) time: {} ms, total transactions: {}", executionTime, createdRandomTransactions.size());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (int i = 0; i < numberOfTransactions; i++) {
            CreateTransactionResponseModel returnValue = modelMapper.map(createdRandomTransactions.get(i), CreateTransactionResponseModel.class);
            generatedTransactions.add(returnValue);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedTransactions);
    }

    // generate transactions by number input via manual mapping
    @PostMapping("/generate/transaction/v2/{numberOfTransactions}")
    public ResponseEntity<List<CreateTransactionResponseModel>> generateTransactionsV2(@PathVariable int numberOfTransactions) {
        // list for generated transactions via CreateTransactionResponseModel
        List<CreateTransactionResponseModel> generatedTransactions = new ArrayList<>();
        // create the random transactions, get the time taken of creation
        long start = System.currentTimeMillis();
        List<TransactionDTO> createdRandomTransactions = transactionsService.createRandomTransactionV2(numberOfTransactions);
        long executionTime = System.currentTimeMillis() - start;
        logger.info("Generate transactions (with manual mapping) time: {} ms, total transactions: {}", executionTime, createdRandomTransactions.size());

        for (int i = 0; i < numberOfTransactions; i++) {
            CreateTransactionResponseModel returnValue = customMapper.toTransactionResponseModel(createdRandomTransactions.get(i));
            generatedTransactions.add(returnValue);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(generatedTransactions);
    }

    // get all user transactions details by user id
    @GetMapping("/{userId}/transactions/details")
    public ResponseEntity<List<TransactionDetailsResponseModel>> getUserTransactionDetails(@PathVariable String userId) {
        List<UserTransactionDetailsDTO> userTransactionDetails = transactionsService.getUserTransactionDetailsByUserId(userId);
        List<TransactionDetailsResponseModel> transactionResponseList = new ArrayList<>();
        for (UserTransactionDetailsDTO userTransactionDetailsDTO : userTransactionDetails) {
            TransactionDetailsResponseModel transactionResponse = customMapper.toTransactionDetailsResponseModel(userTransactionDetailsDTO);
            transactionResponseList.add(transactionResponse);
        }
        return ResponseEntity.ok(transactionResponseList);
    }

    // get all transactions
    @GetMapping("/all")
    public ResponseEntity<List<TransactionDetailsResponseModel>> getAllTransactions() {
        long start = System.currentTimeMillis();
        List<UserTransactionDetailsDTO> transactionList = transactionsService.getAllTransactions();
        List<TransactionDetailsResponseModel> transactionResponseList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (UserTransactionDetailsDTO userTransactionDetailsDTO : transactionList) {
            TransactionDetailsResponseModel transactionResponse = modelMapper.map(userTransactionDetailsDTO, TransactionDetailsResponseModel.class);
            transactionResponseList.add(transactionResponse);
        }
        long executionTime = System.currentTimeMillis() - start;
        logger.info("[TransactionsController] getAllTransactions (model mapper) time: {} ms, total transactions: {}", executionTime, transactionResponseList.size());
        return ResponseEntity.ok(transactionResponseList);
    }

    // get all transactions via manual mapper
    @GetMapping("/all/v2")
    public ResponseEntity<List<TransactionDetailsResponseModel>> getAllTransactionsV2() {
        long start = System.currentTimeMillis();
        List<UserTransactionDetailsDTO> transactionList = transactionsService.getAllTransactions();
        List<TransactionDetailsResponseModel> transactionResponseList = new ArrayList<>();
        for (UserTransactionDetailsDTO userTransactionDetailsDTO : transactionList) {
            TransactionDetailsResponseModel transactionResponse = customMapper.toTransactionDetailsResponseModel(userTransactionDetailsDTO);
            transactionResponseList.add(transactionResponse);
        }
        long executionTime = System.currentTimeMillis() - start;
        logger.info("[TransactionsController] getAllTransactions (manual mapper) time: {} ms, total transactions: {}", executionTime, transactionResponseList.size());
        return ResponseEntity.ok(transactionResponseList);
    }

    // delete transactions in the database based on number input
    @DeleteMapping("/delete/transaction/rows/{numberOfTransactions}")
    public ResponseEntity<Void> deleteTransactionsByAmount(@PathVariable int numberOfTransactions) {
        transactionsService.deleteTransactionsByAmount(numberOfTransactions);
        return ResponseEntity.noContent().build();
    }
}
