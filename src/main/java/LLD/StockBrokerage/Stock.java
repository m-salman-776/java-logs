package LLD.StockBrokerage;

import java.util.ArrayList;
import java.util.List;

// Base class for all types of orders
abstract class Order {
    protected String symbol;
    protected int quantity;
    protected double price; // For limit orders
    protected String type; // Buy or Sell

    public Order(String symbol, int quantity, double price, String type) {
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
    }

    public abstract void execute();
}

// Represents a market order
class MarketOrder extends Order {
    public MarketOrder(String symbol, int quantity, String type) {
        super(symbol, quantity, 0, type); // Price is not relevant for market orders
    }

    @Override
    public void execute() {
        // Execute the market order immediately at the best available price
        // Implementation varies based on exchange's matching algorithm
        System.out.println("Executing Market Order for " + quantity + " shares of " + symbol + " (" + type + ")");
    }
}

// Represents a limit order
class LimitOrder extends Order {
    public LimitOrder(String symbol, int quantity, double price, String type) {
        super(symbol, quantity, price, type);
    }

    @Override
    public void execute() {
        // Execute the limit order at the specified price or better
        // Implementation varies based on exchange's matching algorithm
        System.out.println("Executing Limit Order for " + quantity + " shares of " + symbol + " at price " + price + " (" + type + ")");
    }
}

// Represents a stock exchange
class Exchange {
    private List<Order> buyOrders;
    private List<Order> sellOrders;
    private OrderMatcher orderMatcher;

    public Exchange(OrderMatcher orderMatcher) {
        buyOrders = new ArrayList<>();
        sellOrders = new ArrayList<>();
        this.orderMatcher = orderMatcher;
    }

    public void addOrder(Order order) {
        if (order.type.equals("Buy")) {
            buyOrders.add(order);
        } else if (order.type.equals("Sell")) {
            sellOrders.add(order);
        }
        orderMatcher.matchOrders(buyOrders, sellOrders);
    }
}

// Class responsible for matching orders
class OrderMatcher {
    public void matchOrders(List<Order> buyOrders, List<Order> sellOrders) {
        // Basic order matching logic for demonstration purposes
        // In a real-world scenario, this logic would be more complex
        for (int i = 0; i < buyOrders.size(); i++) {
            for (int j = 0; j < sellOrders.size(); j++) {
                Order buyOrder = buyOrders.get(i);
                Order sellOrder = sellOrders.get(j);
                // Match buy and sell orders based on specific conditions (e.g., price)
                if (buyOrder.price >= sellOrder.price) {
                    // Execute trade
                    System.out.println("Matched: " + buyOrder.symbol + " (Buy) with " + sellOrder.symbol + " (Sell)");
                    buyOrders.remove(i);
                    sellOrders.remove(j);
                    i--; // Adjust index after removal
                    break; // Move to the next buy order
                }
            }
        }
    }
}

// Main class for testing
//public class Main {
//    public static void main(String[] args) {
//        // Create an instance of the order matcher
//        OrderMatcher orderMatcher = new OrderMatcher();
//
//        // Create an instance of the exchange with the order matcher dependency
//        Exchange exchange = new Exchange(orderMatcher);
//
//        // Add some orders to the exchange
//        exchange.addOrder(new MarketOrder("AAPL", 100, "Buy"));
//        exchange.addOrder(new MarketOrder("AAPL", 50, "Sell"));
//        exchange.addOrder(new LimitOrder("GOOGL", 50, 2800.00, "Buy"));
//        exchange.addOrder(new LimitOrder("GOOGL", 30, 2900.00, "Sell"));
//    }
//}
