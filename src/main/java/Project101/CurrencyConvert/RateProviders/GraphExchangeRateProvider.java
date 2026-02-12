package Project101.CurrencyConvert.RateProviders;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;

public class GraphExchangeRateProvider implements ExchangeRateProvider {
    // Adjacency list: Currency -> (Neighbor -> Rate)
    private final Map<String, Map<String, BigDecimal>> rates = new HashMap<>();

    public void addRate(String from, String to, BigDecimal rate) {
        rates.computeIfAbsent(from, k -> new HashMap<>()).put(to, rate);
        // Automatically add the inverse rate (1 / rate)
        BigDecimal inverseRate = BigDecimal.ONE.divide(rate, MathContext.DECIMAL128);
        rates.computeIfAbsent(to, k -> new HashMap<>()).put(from, inverseRate);
    }

    @Override
    public BigDecimal getRate(String from, String to) {
        if (from.equals(to)) return BigDecimal.ONE;

        // BFS to find the conversion path
        Queue<RateNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(new RateNode(from, BigDecimal.ONE));
        visited.add(from);

        while (!queue.isEmpty()) {
            RateNode parent = queue.poll();

            if (parent.currency.equals(to)) {
                return parent.rate;
            }

            Map<String, BigDecimal> neighbors = rates.getOrDefault(parent.currency, Collections.emptyMap());
            for (Map.Entry<String, BigDecimal> child : neighbors.entrySet()) {
                if (!visited.contains(child.getKey())) {
                    visited.add(child.getKey());
                    // Cumulative rate = currentRate * neighborRate
                    queue.add(new RateNode(child.getKey(), parent.rate.multiply(child.getValue())));
                }
            }
        }

        throw new IllegalArgumentException("No exchange rate found between " + from + " and " + to);
    }

    private static class RateNode {
        String currency;
        BigDecimal rate;
        RateNode(String currency, BigDecimal rate) {
            this.currency = currency;
            this.rate = rate;
        }
    }
}