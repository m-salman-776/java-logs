package Project101.CurrencyConvert.RateProviders;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FixedExchangeRateProvider implements ExchangeRateProvider {
    private final Map<String, BigDecimal> rates = new HashMap<>();

    public void addRate(String from, String to, BigDecimal rate) {
        rates.put(from + "-" + to, rate);
    }

    @Override
    public BigDecimal getRate(String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            return BigDecimal.ONE;
        }
        BigDecimal rate = rates.get(fromCurrency + "-" + toCurrency);
        if (rate == null) {
            throw new IllegalArgumentException("Exchange rate not defined for " + fromCurrency + " -> " + toCurrency);
        }
        return rate;
    }
}