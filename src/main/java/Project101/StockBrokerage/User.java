package Project101.StockBrokerage;

import java.util.Date;

public class User {
//    Account account;
//    HashMap<String,WatchList> watchListHashMap;
//    List<Statements> statements;
//    void addStockToWatchList(Stock stock,String watchList){
//        watchListHashMap.get(watchList).addStockToWatchList(stock);
//    }
}

abstract class  Statements {
    String name;
    String description;
    abstract void generate(Date start, Date end);
}

class MonthlyStatements extends Statements{

    @Override
    void generate(Date start, Date end) {

    }
}
class HalfYearlyStatements extends Statements{
    @Override
    void generate(Date start, Date end) {

    }
}


