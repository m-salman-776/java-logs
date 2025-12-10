package Project101.Payment.dto;

import lombok.Getter;

import java.time.YearMonth;

@Getter
public class CreditCard {
    // Getters for all fields
    private final String cardNumber;
    private final String cardHolderName;
    private final YearMonth expiryDate;
    private final String cvv;

    public CreditCard(String cardNumber, String cardHolderName, YearMonth expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    public void validate() {
        if (cardNumber == null || !cardNumber.matches("\\d{16}")) {
            throw new IllegalArgumentException("Invalid card number.");
        }
        if (cardHolderName == null || cardHolderName.trim().isEmpty()) {
            throw new IllegalArgumentException("Card holder name cannot be empty.");
        }
        if (expiryDate == null || expiryDate.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("Card is expired.");
        }
        if (cvv == null || !cvv.matches("\\d{3}")) {
            throw new IllegalArgumentException("Invalid CVV.");
        }
        System.out.println("Credit card validation successful.");
    }
}
