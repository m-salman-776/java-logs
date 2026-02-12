package Project101.CurrencyConvert;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Money {
    private final BigDecimal amount;
    private final String currency;

    public Money(BigDecimal amount,String currency){
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money convert(BigDecimal rate, String targetCurrency) {
        if (rate == null || rate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Exchange rate must be non-negative");
        }
        // Standard financial rounding (2 decimal places, Half-Up)
        BigDecimal newAmount = amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return new Money(newAmount, targetCurrency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }
}
