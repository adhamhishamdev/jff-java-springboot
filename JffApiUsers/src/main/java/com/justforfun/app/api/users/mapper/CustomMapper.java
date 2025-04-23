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

public interface CustomMapper {

    UserDTO toUserDTO (UserEntity userEntity);
    UserDTO toUserDTO (CreateUserRequestModel userBodyRequest);
    UserEntity toUserEntity (UserDTO userDTO);
    UserResponseModel toUserResponseModel (UserDTO userDTO);
    UserInfoResponseModel toUserInfoModel(UserDTO userDTO);
    CreateUserResponseModel toCreateUserResponseModel (UserDTO userDTO);

    CreditCardDTO toCreditCardDTO (CreditCardEntity creditCardEntity);
    CreditCardDTO toCreditCardDTO (CreateCreditCardRequestModel creditCardBodyRequest);
    CreditCardEntity toCreditCardEntity (CreditCardDTO creditCardDTO);
    CreateCreditCardResponseModel toCardResponseModel (CreditCardDTO creditCardDTO);

    TransactionDTO toTransactionDTO (TransactionEntity transactionEntity);
    TransactionDTO toTransactionDTO (CreateTransactionRequestModel transactionBodyRequest);
    TransactionEntity toTransactionEntity (TransactionDTO transactionDTO);
    TransactionDetailsResponseModel toTransactionDetailsResponseModel (UserTransactionDetailsDTO userTransactionDetailsDTO);
    CreateTransactionResponseModel toTransactionResponseModel (TransactionDTO transactionDTO);
}
