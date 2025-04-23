package com.justforfun.app.api.users.service.creditcard;

import com.justforfun.app.api.users.data.creditcard.CreditCardEntity;
import com.justforfun.app.api.users.data.creditcard.CreditCardsRepository;
import com.justforfun.app.api.users.data.user.UserEntity;
import com.justforfun.app.api.users.data.user.UsersRepository;
import com.justforfun.app.api.users.mapper.CustomMapper;
import com.justforfun.app.api.users.service.users.UsersService;
import com.justforfun.app.api.users.shared.CreditCardDTO;
import com.justforfun.app.api.users.utils.CreditCardUtils;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreditCardsServiceImpl implements CreditCardsService {

    CreditCardsRepository creditCardsRepository;
    UsersRepository usersRepository;
    Environment environment;

    @Autowired
    UsersService usersService;

    @Autowired
    CustomMapper customMapper;

    @Autowired
    public CreditCardsServiceImpl(CreditCardsRepository creditCardsRepository, Environment environment, UsersRepository usersRepository)
    {
        this.creditCardsRepository = creditCardsRepository;
        this.environment = environment;
        this.usersRepository = usersRepository;
    }

    // create a card
    @Override
    public CreditCardDTO createCreditCard(CreditCardDTO creditCardDetails) {
        UserEntity userEntity = usersRepository.findByUserId(creditCardDetails.getUserId());
        if(userEntity == null) {
            throw new UsernameNotFoundException("User with userId " + creditCardDetails.getUserId() + " not found");
        }
        creditCardDetails.setCvv("123");
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        CreditCardEntity creditCardEntity = modelMapper.map(creditCardDetails, CreditCardEntity.class);
        creditCardEntity.setUser(userEntity);
        creditCardsRepository.save(creditCardEntity);
        CreditCardDTO returnValue = modelMapper.map(creditCardEntity, CreditCardDTO.class);
        returnValue.setUserId(userEntity.getUserId());
        return returnValue;
    }

    // create a card via manual mapping
    @Override
    public CreditCardDTO createCreditCardV2(CreditCardDTO creditCardDetails) {
        UserEntity userEntity = usersRepository.findByUserId(creditCardDetails.getUserId());
        if(userEntity == null) {
            throw new UsernameNotFoundException("User with userId " + creditCardDetails.getUserId() + " not found");
        }
        creditCardDetails.setCvv("123");
        CreditCardEntity creditCardEntity = customMapper.toCreditCardEntity(creditCardDetails);
        creditCardEntity.setUser(userEntity);
        creditCardsRepository.save(creditCardEntity);
        CreditCardDTO returnValue = customMapper.toCreditCardDTO(creditCardEntity);
        return returnValue;
    }

    // create random cards by number input
    @Override
    public List<CreditCardDTO> createRandomCard(int numberOfCards) {
        // create a list for created random cards
        List<CreditCardDTO> createdRandomCards = new ArrayList<>();
        // fetch all user ids from the database
        List<String> userIdsFromDatabase = usersService.getAllUserId();
        // just checking purpose, to avoid creating the same card number, refer CardCreditUtils
        Set<String> existingCardNumbers = new HashSet<>();
        Random random = new Random();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        for (int i = 0; i < numberOfCards; i++) {
            CreditCardDTO cardDetails = new CreditCardDTO();
            cardDetails.setCardHolderName("Adham Hisham");
            cardDetails.setExpirationDate("01/32");
            cardDetails.setCvv("123");
            // generate a unique 16-digit card number
            String uniqueCardNumber = CreditCardUtils.generateUniqueCardNumber(existingCardNumbers);
            cardDetails.setCardNumber(uniqueCardNumber);
            // based on the list of all user ids, select one random id, and find a user entity based on the id
            String randomUserId = userIdsFromDatabase.get(random.nextInt(userIdsFromDatabase.size()));
            UserEntity selectedUserEntity = usersRepository.findByUserId(randomUserId);
            CreditCardEntity creditCardEntity = modelMapper.map(cardDetails, CreditCardEntity.class);
            creditCardEntity.setUser(selectedUserEntity);
            creditCardsRepository.save(creditCardEntity);
            CreditCardDTO returnValue = modelMapper.map(creditCardEntity, CreditCardDTO.class);
            returnValue.setUserId(selectedUserEntity.getUserId());
            createdRandomCards.add(returnValue);
        }
        return createdRandomCards;
    }

    // create random cards by number input via manual mapper
    @Override
    public List<CreditCardDTO> createRandomCardV2(int numberOfCards) {
        // create a list for created random cards
        List<CreditCardDTO> createdRandomCards = new ArrayList<>();
        // fetch all user ids from the database
        List<String> userIdsFromDatabase = usersService.getAllUserId();
        // just checking purpose, to avoid creating the same card number, refer CardCreditUtils
        Set<String> existingCardNumbers = new HashSet<>();
        Random random = new Random();
        for (int i = 0; i < numberOfCards; i++) {
            CreditCardDTO cardDetails = new CreditCardDTO();
            cardDetails.setCardHolderName("Adham Hisham");
            cardDetails.setExpirationDate("01/32");
            cardDetails.setCvv("123");
            // generate a unique 16-digit card number
            String uniqueCardNumber = CreditCardUtils.generateUniqueCardNumber(existingCardNumbers);
            cardDetails.setCardNumber(uniqueCardNumber);
            // based on the list of all user ids, select one random id, and find a user entity based on the id
            String randomUserId = userIdsFromDatabase.get(random.nextInt(userIdsFromDatabase.size()));
            UserEntity selectedUserEntity = usersRepository.findByUserId(randomUserId); // to match with CreditCardEntity defined
            CreditCardEntity creditCardEntity = customMapper.toCreditCardEntity(cardDetails);
            creditCardEntity.setUser(selectedUserEntity);
            creditCardsRepository.save(creditCardEntity);
            CreditCardDTO cardReturnValue = customMapper.toCreditCardDTO(creditCardEntity);
            createdRandomCards.add(cardReturnValue);
        }
        return createdRandomCards;
    }

    // delete cards in the database by number input
    @Transactional
    @Override
    public void deleteCardsByAmount(int numberOfCards) {
        creditCardsRepository.deleteCardsByAmount(numberOfCards);
    }

    // get all credit card numbers in a list
    @Override
    public List<String> getAllCreditCardNumbers() {
        List<String> listOfCreditCardNumbers = creditCardsRepository.findAllCreditCardNumbers();
        return listOfCreditCardNumbers;
    }
}
