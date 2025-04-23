package com.justforfun.app.api.users.utils;

import java.util.Random;
import java.util.Set;

public class CreditCardUtils {
    private static final Random random = new Random();

    public static String generateUniqueCardNumber(Set<String> existingNumbers) {
        String cardNumber;
        do {
            cardNumber = generateRandomCardNumber();

        } while (existingNumbers.contains(cardNumber));

        existingNumbers.add(cardNumber);

        return cardNumber;
    }

    private static String generateRandomCardNumber() {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
