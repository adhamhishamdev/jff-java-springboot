package com.justforfun.app.api.users.shared;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserTransactionDetailsDTO implements Serializable {

    private static final long serialVersionUID = -953297098295050686L;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String cardNumber;
    private String expirationDate;
    private String cardHolderName;
    private BigDecimal transactionAmount;
    private LocalDateTime transactionDate;
    private String transactionId;

    public UserTransactionDetailsDTO(String userId, String firstName, String lastName, String email,
                          String cardNumber, String expirationDate, String cardHolderName,
                          BigDecimal transactionAmount, LocalDateTime transactionDate, String transactionId) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cardHolderName = cardHolderName;
        this.transactionAmount = transactionAmount;
        this.transactionDate = transactionDate;
        this.transactionId = transactionId;
    }

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
