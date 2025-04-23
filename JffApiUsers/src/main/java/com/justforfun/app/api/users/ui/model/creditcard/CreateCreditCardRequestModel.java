package com.justforfun.app.api.users.ui.model.creditcard;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateCreditCardRequestModel {
    @NotNull(message = "Card number cannot be null")
    @Size(min = 16, max = 16, message = "Card number must be exactly 16 characters")
    @Pattern(regexp = "\\d{16}", message = "Card number must be numeric")
    private String cardNumber;

    @NotNull(message = "Expiration date cannot be null")
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiration date must be in MM/YY format")
    private String expirationDate;

    @NotNull(message = "Card holder name cannot be null")
    @Size(min = 2, message = "Card holder name must not be less than two characters")
    private String cardHolderName;

    @NotNull(message = "User ID cannot be null")
    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
