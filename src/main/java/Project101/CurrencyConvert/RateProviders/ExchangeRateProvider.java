package Project101.CurrencyConvert.RateProviders;

import java.math.BigDecimal;

public interface ExchangeRateProvider {
    BigDecimal getRate(String fromCurrency, String toCurrency);
}