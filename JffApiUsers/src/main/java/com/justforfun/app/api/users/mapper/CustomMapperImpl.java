package com.justforfun.app.api.users.mapper;

import com.justforfun.app.api.users.data.creditcard.CreditCardEntity;
import com.justforfun.app.api.users.data.transaction.TransactionEntity;
import com.justforfun.app.api.users.data.user.UserEntity;
import com.justforfun.app.api.users.shared.CreditCardDTO;
import com.justforfun.app.api.users.shared.TransactionDTO;
import com.justforfun.app.api.users.shared.UserDTO;
import com.justforfun.app.api.users.shared.UserTransactionDetailsDTO;
import com.justforfun.app.api.users.ui.model.creditcard.CreateCreditCardRequestModel;
import com.justforfun.app.api.users.ui.model.creditcard.CreateCreditCardResponseModel;
import com.justforfun.app.api.users.ui.model.transaction.CreateTransactionRequestModel;
import com.justforfun.app.api.users.ui.model.transaction.CreateTransactionResponseModel;
import com.justforfun.app.api.users.ui.model.transaction.TransactionDetailsResponseModel;
import com.justforfun.app.api.users.ui.model.user.CreateUserRequestModel;
import com.justforfun.app.api.users.ui.model.user.CreateUserResponseModel;
import com.justforfun.app.api.users.ui.model.user.UserInfoResponseModel;
import com.justforfun.app.api.users.ui.model.user.UserResponseModel;
import org.springframework.stereotype.Service;

@Service
public class CustomMapperImpl implements CustomMapper{

    @Override
    public UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userEntity.getUserId());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setCreditCards(userEntity.getCreditCards());
        userDTO.setTransactions(userEntity.getTransactions());
        return userDTO;
    }

    public UserDTO toUserDTO (CreateUserRequestModel userDetails) {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName(userDetails.getFirstName());
        userDTO.setLastName(userDetails.getLastName());
        userDTO.setPassword(userDetails.getPassword());
        userDTO.setEmail(userDetails.getEmail());
        return userDTO;
    }

    @Override
    public UserEntity toUserEntity(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(userDTO.getUserId());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setEncryptedPassword(userDTO.getEncryptedPassword());
        return userEntity;
    }

    @Override
    public UserResponseModel toUserResponseModel (UserDTO userDTO) {
        UserResponseModel userResponseModel = new UserResponseModel();
        userResponseModel.setFirstName(userDTO.getFirstName());
        userResponseModel.setLastName(userDTO.getLastName());
        userResponseModel.setEmail(userDTO.getEmail());
        userResponseModel.setUserId(userDTO.getUserId());
        return userResponseModel;
    }

    @Override
    public UserInfoResponseModel toUserInfoModel (UserDTO userDTO) {
        UserInfoResponseModel userDataModel = new UserInfoResponseModel();
        userDataModel.setFirstName(userDTO.getFirstName());
        userDataModel.setLastName(userDTO.getLastName());
        userDataModel.setEmail(userDTO.getEmail());
        userDataModel.setUserId(userDTO.getUserId());
        userDataModel.setCreditCards(userDTO.getCreditCards());
        userDataModel.setTransactions(userDTO.getTransactions());
        return userDataModel;
    }

    public CreateUserResponseModel toCreateUserResponseModel (UserDTO userDTO) {
        CreateUserResponseModel userResponseModel = new CreateUserResponseModel();
        userResponseModel.setFirstName(userDTO.getFirstName());
        userResponseModel.setLastName(userDTO.getLastName());
        userResponseModel.setEmail(userDTO.getEmail());
        userResponseModel.setUserId(userDTO.getUserId());
        return userResponseModel;
    }

    @Override
    public CreditCardDTO toCreditCardDTO(CreditCardEntity creditCardEntity) {
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setCardNumber(creditCardEntity.getCardNumber());
        creditCardDTO.setCardHolderName(creditCardEntity.getCardHolderName());
        creditCardDTO.setExpirationDate(creditCardEntity.getExpirationDate());
        creditCardDTO.setCvv(creditCardEntity.getCvv());
        creditCardDTO.setUserId(creditCardEntity.getUser().getUserId());
        return creditCardDTO;
    }

    @Override
    public CreditCardDTO toCreditCardDTO(CreateCreditCardRequestModel creditCardDetails) {
        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setCardNumber(creditCardDetails.getCardNumber());
        creditCardDTO.setCardHolderName(creditCardDTO.getCardHolderName());
        creditCardDTO.setExpirationDate(creditCardDTO.getExpirationDate());
        creditCardDTO.setUserId(creditCardDTO.getUserId());
        return creditCardDTO;
    }

    @Override
    public CreditCardEntity toCreditCardEntity(CreditCardDTO creditCardDTO) {
        CreditCardEntity creditCardEntity = new CreditCardEntity();
        creditCardEntity.setCardNumber(creditCardDTO.getCardNumber());
        creditCardEntity.setCardHolderName(creditCardDTO.getCardHolderName());
        creditCardEntity.setExpirationDate(creditCardDTO.getExpirationDate());
        creditCardEntity.setCvv(creditCardDTO.getCvv());
        return creditCardEntity;
    }

    @Override
    public CreateCreditCardResponseModel toCardResponseModel(CreditCardDTO creditCardDTO) {
        CreateCreditCardResponseModel cardResponseModel = new CreateCreditCardResponseModel();
        cardResponseModel.setCardNumber(creditCardDTO.getCardNumber());
        cardResponseModel.setCardHolderName(creditCardDTO.getCardHolderName());
        cardResponseModel.setExpirationDate(creditCardDTO.getExpirationDate());
        cardResponseModel.setUserId(creditCardDTO.getUserId());
        return cardResponseModel;
    }

    @Override
    public TransactionDTO toTransactionDTO(TransactionEntity transactionEntity) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(transactionEntity.getAmount());
        transactionDTO.setTransactionId(transactionEntity.getTransactionId());
        transactionDTO.setTransactionDate(transactionEntity.getTransactionDate());
        transactionDTO.setUserId(transactionEntity.getUser().getUserId());
        transactionDTO.setCreditCardNumber(transactionEntity.getCreditCard().getCardNumber());
        return transactionDTO;
    }

    @Override
    public TransactionDTO toTransactionDTO(CreateTransactionRequestModel transactionBodyRequest) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(transactionBodyRequest.getAmount());
        transactionDTO.setUserId(transactionBodyRequest.getUserId());
        transactionDTO.setCreditCardNumber(transactionBodyRequest.getCreditCardNumber());
        return transactionDTO;
    }

    @Override
    public TransactionEntity toTransactionEntity(TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setTransactionId(transactionDTO.getTransactionId());
        transactionEntity.setAmount(transactionDTO.getAmount());
        transactionEntity.setTransactionDate(transactionDTO.getTransactionDate());
        return transactionEntity;
    }

    @Override
    public TransactionDetailsResponseModel toTransactionDetailsResponseModel(UserTransactionDetailsDTO userTransactionDetails) {
        TransactionDetailsResponseModel transactionResponseModel = new TransactionDetailsResponseModel();
        transactionResponseModel.setUserId(userTransactionDetails.getUserId());
        transactionResponseModel.setFirstName(userTransactionDetails.getFirstName());
        transactionResponseModel.setLastName(userTransactionDetails.getLastName());
        transactionResponseModel.setEmail(userTransactionDetails.getEmail());
        transactionResponseModel.setCardNumber(userTransactionDetails.getCardNumber());
        transactionResponseModel.setExpirationDate(userTransactionDetails.getExpirationDate());
        transactionResponseModel.setCardHolderName(userTransactionDetails.getCardHolderName());
        transactionResponseModel.setTransactionAmount(userTransactionDetails.getTransactionAmount());
        transactionResponseModel.setTransactionId(userTransactionDetails.getTransactionId());
        transactionResponseModel.setTransactionDate(userTransactionDetails.getTransactionDate());
        return transactionResponseModel;
    }

    @Override
    public CreateTransactionResponseModel toTransactionResponseModel(TransactionDTO transactionDTO) {
        CreateTransactionResponseModel transactionResponseModel = new CreateTransactionResponseModel();
        transactionResponseModel.setAmount(transactionDTO.getAmount());
        transactionResponseModel.setTransactionId(transactionDTO.getTransactionId());
        transactionResponseModel.setTransactionDate(transactionDTO.getTransactionDate());
        transactionResponseModel.setUserId(transactionDTO.getUserId());
        transactionResponseModel.setCreditCardNumber(transactionDTO.getCreditCardNumber());
        return transactionResponseModel;
    }

}
