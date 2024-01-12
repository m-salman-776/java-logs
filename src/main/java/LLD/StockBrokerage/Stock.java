package LLD.StockBrokerage;

import java.util.List;

public class Stock {
}

class WatchList {
    List<Stock> stockList;
    public void addStockToWatchList(Stock stock){
        stockList.add(stock);
    }
}

enum Status {
    DONE,
    PENDING,
    SENT,
    REJECTED,
}

abstract class Order {
    Status status;
}