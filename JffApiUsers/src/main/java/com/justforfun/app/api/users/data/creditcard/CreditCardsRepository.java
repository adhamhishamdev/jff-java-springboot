package com.justforfun.app.api.users.data.creditcard;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreditCardsRepository extends CrudRepository<CreditCardEntity, Long> {

    CreditCardEntity findByCardNumber(String cardNumber);

    @Modifying
    @Query("DELETE FROM CreditCardEntity creditCard " +
            " WHERE creditCard.id IN " +
            "( SELECT creditCardToBeDeleted.id FROM " +
            "( SELECT creditCardBelowOnList.id AS id FROM CreditCardEntity creditCardBelowOnList ORDER BY creditCardBelowOnList.id DESC) " +
            "creditCardToBeDeleted WHERE ROWNUM <= :numberOfCards ) ")
    void deleteCardsByAmount(@Param("numberOfCards") int numberOfCards);

    @Query("SELECT creditCard.cardNumber FROM CreditCardEntity creditCard")
    List<String> findAllCreditCardNumbers();

}