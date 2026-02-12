package Project101.CurrencyConvert;

import Project101.CurrencyConvert.RateProviders.ApiBasedExchangeRateProvider;
import Project101.CurrencyConvert.RateProviders.FixedExchangeRateProvider;
import Project101.CurrencyConvert.RateProviders.GraphExchangeRateProvider;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // 1. Setup Provider with some base rates
        GraphExchangeRateProvider provider = new GraphExchangeRateProvider();
        provider.addRate("USD", "EUR", new BigDecimal("0.94"));
        provider.addRate("EUR", "GBP", new BigDecimal("0.86"));
        provider.addRate("USD", "INR", new BigDecimal("83.0"));

        // 2. Initialize Service
        CurrencyConverter converter = new CurrencyConverter(provider);

        // 3. Perform Conversions
        Money usd = new Money(new BigDecimal("100"), "USD");

        // Direct conversion
        Money inr = converter.convert(usd, "INR");
        System.out.println(usd + " = " + inr);

        // Transitive conversion (USD -> EUR -> GBP)
        // The graph logic automatically finds the path!
        Money gbp = converter.convert(usd, "GBP");
        System.out.println(usd + " = " + gbp);

        // 4. Using the new Fixed Provider
        System.out.println("\n--- Fixed Provider ---");
        FixedExchangeRateProvider fixedProvider = new FixedExchangeRateProvider();
        fixedProvider.addRate("USD", "JPY", new BigDecimal("110.50"));

        CurrencyConverter fixedConverter = new CurrencyConverter(fixedProvider);
        Money jpy = fixedConverter.convert(usd, "JPY");
        System.out.println(usd + " = " + jpy);
        testApiBasedProvider();
        testGraphBasedProvider();
    }
    static void testApiBasedProvider(){
        ApiBasedExchangeRateProvider provider = new ApiBasedExchangeRateProvider();
        CurrencyConverter converter = new CurrencyConverter(provider);
        Money source = new Money(new BigDecimal(12),"INR");

        Money usd = converter.convert(source,"USD");
        System.out.println(usd);
    }
    static void testGraphBasedProvider(){
        GraphExchangeRateProvider provider = new GraphExchangeRateProvider();
        provider.addRate("USD","INR",new BigDecimal(90));
        provider.addRate("USD","DRH",new BigDecimal(3));
        provider.addRate("USD","RYL",new BigDecimal("0.5"));

        CurrencyConverter converter = new CurrencyConverter(provider);

        Money inr = new Money(new BigDecimal(12),"INR");
        Money ryl = converter.convert(inr,"RYL");
        System.out.println("DONE");
    }
}