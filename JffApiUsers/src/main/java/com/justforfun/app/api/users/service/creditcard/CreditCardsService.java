package com.justforfun.app.api.users.service.creditcard;

import com.justforfun.app.api.users.shared.CreditCardDTO;

import java.util.List;

public interface CreditCardsService {
    CreditCardDTO createCreditCard(CreditCardDTO creditCardDetails);
    CreditCardDTO createCreditCardV2(CreditCardDTO creditCardDetails);
    List<CreditCardDTO> createRandomCard(int numberOfCards);
    List<CreditCardDTO> createRandomCardV2(int numberOfCards);
    void deleteCardsByAmount(int numberOfCards);
    List<String> getAllCreditCardNumbers();
}
