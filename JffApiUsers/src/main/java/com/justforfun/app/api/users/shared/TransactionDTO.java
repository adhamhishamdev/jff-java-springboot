package com.justforfun.app.api.users.shared;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO implements Serializable {

    private static final long serialVersionUID = -953297098295050686L;
    private BigDecimal amount;
    private LocalDateTime transactionDate;
    private String transactionId;
    private String userId;
    private String creditCardNumber;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
