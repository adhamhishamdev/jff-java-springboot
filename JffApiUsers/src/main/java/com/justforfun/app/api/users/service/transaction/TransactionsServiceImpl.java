package com.justforfun.app.api.users.service.transaction;

import com.justforfun.app.api.users.data.creditcard.CreditCardEntity;
import com.justforfun.app.api.users.data.creditcard.CreditCardsRepository;
import com.justforfun.app.api.users.data.transaction.TransactionEntity;
import com.justforfun.app.api.users.data.transaction.TransactionsRepository;
import com.justforfun.app.api.users.data.user.UserEntity;
import com.justforfun.app.api.users.data.user.UsersRepository;
import com.justforfun.app.api.users.mapper.CustomMapper;
import com.justforfun.app.api.users.service.users.UsersService;
import com.justforfun.app.api.users.service.users.UsersServiceImpl;
import com.justforfun.app.api.users.shared.TransactionDTO;
import com.justforfun.app.api.users.shared.UserTransactionDetailsDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    CreditCardsRepository creditCardsRepository;
    UsersRepository usersRepository;
    TransactionsRepository transactionsRepository;
    Environment environment;

    Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    UsersService usersService;

    @Autowired
    CustomMapper customMapper;

    @Autowired
    public TransactionsServiceImpl(CreditCardsRepository creditCardsRepository,
                                   Environment environment,
                                   UsersRepository usersRepository,
                                   TransactionsRepository transactionsRepository)
    {
        this.creditCardsRepository = creditCardsRepository;
        this.environment = environment;
        this.usersRepository = usersRepository;
        this.transactionsRepository = transactionsRepository;
    }

    // create a transaction
    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDetails) {
        // find user entity and credit card entity
        UserEntity userEntity = usersRepository.findByUserId(transactionDetails.getUserId());
        CreditCardEntity creditCardEntity = creditCardsRepository.findByCardNumber(transactionDetails.getCreditCardNumber());
        if(userEntity == null) {
            throw new UsernameNotFoundException("User with userId " + transactionDetails.getUserId() + " not found");
        }
        // generate transactionId and the transactionDate
        transactionDetails.setTransactionId(UUID.randomUUID().toString());
        transactionDetails.setTransactionDate(LocalDateTime.now());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // map dto to entity, add another absent fields
        TransactionEntity transactionEntity = modelMapper.map(transactionDetails, TransactionEntity.class);
        transactionEntity.setUser(userEntity);
        transactionEntity.setCreditCard(creditCardEntity);
        transactionsRepository.save(transactionEntity);
        TransactionDTO returnValue = modelMapper.map(transactionEntity, TransactionDTO.class);
        returnValue.setUserId(userEntity.getUserId());
        returnValue.setCreditCardNumber(creditCardEntity.getCardNumber());
        return returnValue;
    }

    // create a transaction via manual mapper
    @Override
    public TransactionDTO createTransactionV2(TransactionDTO transactionDetails) {
        // find user entity and credit card entity
        UserEntity userEntity = usersRepository.findByUserId(transactionDetails.getUserId());
        CreditCardEntity creditCardEntity = creditCardsRepository.findByCardNumber(transactionDetails.getCreditCardNumber());
        if(userEntity == null) {
            throw new UsernameNotFoundException("User with userId " + transactionDetails.getUserId() + " not found");
        }
        // generate transactionId and the transactionDate
        transactionDetails.setTransactionId(UUID.randomUUID().toString());
        transactionDetails.setTransactionDate(LocalDateTime.now());
        TransactionEntity transactionEntity = customMapper.toTransactionEntity(transactionDetails);
        transactionEntity.setUser(userEntity);
        transactionEntity.setCreditCard(creditCardEntity);
        transactionsRepository.save(transactionEntity);
        TransactionDTO returnValue = customMapper.toTransactionDTO(transactionEntity);
        return returnValue;
    }

    // create random transactions via number input
    @Override
    public List<TransactionDTO> createRandomTransaction(int numberOfTransactions) {
        // create list for created random transactions
        List<TransactionDTO> createdRandomTransactions = new ArrayList<>();
        // fetch all user IDs from the database
        List<String> userIdsFromDatabase = usersService.getAllUserId();
        Random random = new Random();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        // create random transactions and save to database
        for (int i = 0; i < numberOfTransactions; i++) {
            TransactionDTO transactionDetails = new TransactionDTO();
            transactionDetails.setTransactionId(UUID.randomUUID().toString());
            transactionDetails.setTransactionDate(LocalDateTime.now());
            transactionDetails.setAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100,5000)));
            // based on the list of all user ids, select one random id, and find a user entity based on the id
            String randomUserId = userIdsFromDatabase.get(random.nextInt(userIdsFromDatabase.size()));
            UserEntity selectedUserEntity = usersRepository.findByUserId(randomUserId);
            // get the credit cards from the selected random user entity, convert to list
            Set<CreditCardEntity> creditCardsOfSelectedUser = selectedUserEntity.getCreditCards();
            List<CreditCardEntity> creditCardList = new ArrayList<>(creditCardsOfSelectedUser);
            // based on the list of credit cards, select one random card
            CreditCardEntity selectedCreditCardEntity = creditCardList.get(random.nextInt(creditCardList.size()));
            TransactionEntity transactionEntity = modelMapper.map(transactionDetails, TransactionEntity.class);
            transactionEntity.setUser(selectedUserEntity);
            transactionEntity.setCreditCard(selectedCreditCardEntity);
            transactionsRepository.save(transactionEntity);
            TransactionDTO returnValue = modelMapper.map(transactionEntity, TransactionDTO.class);
            returnValue.setUserId(selectedUserEntity.getUserId());
            returnValue.setCreditCardNumber(selectedCreditCardEntity.getCardNumber());
            createdRandomTransactions.add(returnValue);
        }
        return createdRandomTransactions;
    }

    // create random transactions by number input via manual mapping
    @Override
    public List<TransactionDTO> createRandomTransactionV2(int numberOfTransactions) {
        // create list for created random transactions
        List<TransactionDTO> createdRandomTransactions = new ArrayList<>();
        // fetch all user IDs from the database
        List<String> userIdsFromDatabase = usersService.getAllUserId();
        Random random = new Random();
        // create random transactions and save to database
        for (int i = 0; i < numberOfTransactions; i++) {
            TransactionDTO transactionDetails = new TransactionDTO();
            transactionDetails.setTransactionId(UUID.randomUUID().toString());
            transactionDetails.setTransactionDate(LocalDateTime.now());
            transactionDetails.setAmount(BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(100,5000)));
            // based on the list of all user ids, select one random id, and find a user entity based on the id
            String randomUserId = userIdsFromDatabase.get(random.nextInt(userIdsFromDatabase.size()));
            UserEntity selectedUserEntity = usersRepository.findByUserId(randomUserId);
            // get the credit cards from the selected random user entity, convert to list
            Set<CreditCardEntity> creditCardsOfSelectedUser = selectedUserEntity.getCreditCards();
            List<CreditCardEntity> creditCardList = new ArrayList<>(creditCardsOfSelectedUser);
            // based on the list of credit cards, select one random card
            CreditCardEntity selectedCreditCardEntity = creditCardList.get(random.nextInt(creditCardList.size()));
            TransactionEntity transactionEntity = customMapper.toTransactionEntity(transactionDetails);
            transactionEntity.setUser(selectedUserEntity);
            transactionEntity.setCreditCard(selectedCreditCardEntity);
            transactionsRepository.save(transactionEntity);
            TransactionDTO returnValue = customMapper.toTransactionDTO(transactionEntity);
            createdRandomTransactions.add(returnValue);
        }
        return createdRandomTransactions;
    }

    // get all user transaction details by user id
    @Override
    public List<UserTransactionDetailsDTO> getUserTransactionDetailsByUserId(String userId) {
        List<UserTransactionDetailsDTO> userDetails = usersRepository.findUserTransactionDetailsByUserId(userId);
        return userDetails;
    }

    // get all transactions from database
    @Override
    public List<UserTransactionDetailsDTO> getAllTransactions() {
        //long start = System.currentTimeMillis();
        List<UserTransactionDetailsDTO> transactionList = transactionsRepository.findAllTransactions();
        //long executionTime = System.currentTimeMillis() - start;
        //logger.info("[TransactionsServiceImpl] getAllTransactions time: {} ms, total transactions: {}", executionTime, transactionList.size());
        return transactionList;
    }

    // delete transactions by number input
    @Transactional
    @Override
    public void deleteTransactionsByAmount(int numberOfTransactions) {
        transactionsRepository.deleteTransactionsByAmount(numberOfTransactions);
    }
}
