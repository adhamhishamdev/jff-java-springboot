package com.justforfun.app.api.users.ui.model.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class CreateTransactionRequestModel {

    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    @NotNull(message = "User ID cannot be null")
    private String userId;

    @NotNull(message = "Card number cannot be null")
    @Size(min = 16, max = 16, message = "Card number must be exactly 16 characters")
    @Pattern(regexp = "\\d{16}", message = "Card number must be numeric")
    private String creditCardNumber;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
