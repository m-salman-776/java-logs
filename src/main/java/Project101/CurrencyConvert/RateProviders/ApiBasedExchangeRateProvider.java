package Project101.CurrencyConvert.RateProviders;

import java.math.BigDecimal;

public class ApiBasedExchangeRateProvider implements ExchangeRateProvider {

    @Override
    public BigDecimal getRate(String fromCurrency, String toCurrency) {
        // Simulate an external API call (e.g., to Stripe or Fixer.io)
        System.out.printf("Calling External API for rate: %s -> %s...%n", fromCurrency, toCurrency);

        // Simulate network latency
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (fromCurrency.equals(toCurrency)) {
            return BigDecimal.ONE;
        }

        // Return a mock rate for demonstration
        return new BigDecimal("0.85");
    }
}