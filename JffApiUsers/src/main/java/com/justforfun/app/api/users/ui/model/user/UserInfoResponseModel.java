package com.justforfun.app.api.users.ui.model.user;

import com.justforfun.app.api.users.data.creditcard.CreditCardEntity;
import com.justforfun.app.api.users.data.transaction.TransactionEntity;
import java.util.Set;

public class UserInfoResponseModel {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Set<CreditCardEntity> creditCards;
    private Set<TransactionEntity> transactions;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public Set<CreditCardEntity> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(Set<CreditCardEntity> creditCards) {
        this.creditCards = creditCards;
    }

}
