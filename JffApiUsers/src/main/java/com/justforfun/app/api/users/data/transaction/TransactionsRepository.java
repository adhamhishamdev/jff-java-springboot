package com.justforfun.app.api.users.data.transaction;

import com.justforfun.app.api.users.shared.UserTransactionDetailsDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionsRepository extends CrudRepository<TransactionEntity, Long> {


    @Query("SELECT new com.justforfun.app.api.users.shared.UserTransactionDetailsDTO(u.userId, u.firstName, u.lastName, u.email, " +
            "cc.cardNumber, cc.expirationDate, cc.cardHolderName, t.amount, t.transactionDate, t.transactionId) " +
            "FROM TransactionEntity t " +
            "LEFT JOIN t.creditCard cc " +
            "LEFT JOIN t.user u ")
    List<UserTransactionDetailsDTO> findAllTransactions();

    @Modifying
    @Query("DELETE FROM TransactionEntity transaction " +
            "WHERE transaction.id IN " +
            "( SELECT transactionToBeDeleted.id FROM " +
            "( SELECT transactionRecent.id AS id FROM TransactionEntity transactionRecent ORDER BY transactionRecent.id DESC) " +
            "transactionToBeDeleted WHERE ROWNUM <= :numberOfTransactions ) ")
    void deleteTransactionsByAmount(@Param("numberOfTransactions") int numberOfTransactions);
}
