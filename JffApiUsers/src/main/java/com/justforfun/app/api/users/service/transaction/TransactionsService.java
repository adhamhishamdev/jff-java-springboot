package com.justforfun.app.api.users.service.transaction;

import com.justforfun.app.api.users.shared.TransactionDTO;
import com.justforfun.app.api.users.shared.UserTransactionDetailsDTO;

import java.util.List;

public interface TransactionsService {

    TransactionDTO createTransaction(TransactionDTO transactionDetails);
    TransactionDTO createTransactionV2(TransactionDTO transactionDetails);
    List<UserTransactionDetailsDTO> getUserTransactionDetailsByUserId(String userId);
    List<TransactionDTO> createRandomTransaction(int numberOfTransaction);
    List<TransactionDTO> createRandomTransactionV2(int numberOfTransaction);
    void deleteTransactionsByAmount(int numberOfTransactions);
    List<UserTransactionDetailsDTO> getAllTransactions();
}
