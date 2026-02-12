package Project101.CurrencyConvert;

import Project101.CurrencyConvert.RateProviders.ExchangeRateProvider;

import java.math.BigDecimal;

public class CurrencyConverter {
    private final ExchangeRateProvider rateProvider;

    public CurrencyConverter(ExchangeRateProvider rateProvider) {
        this.rateProvider = rateProvider;
    }

    public Money convert(Money source, String targetCurrency) {
        BigDecimal rate = rateProvider.getRate(source.getCurrency(), targetCurrency);
        return source.convert(rate, targetCurrency);
    }

}