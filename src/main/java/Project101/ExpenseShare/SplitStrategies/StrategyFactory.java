package Project101.ExpenseShare.SplitStrategies;


import Project101.ExpenseShare.SplitType;

public class StrategyFactory {
    public static SplitStrategy getSplitStrategy(SplitType splitType){
        switch (splitType){
            case EQUAL: return new EqualStrategy();
            case EXACT: return new ExactStrategy();
            case PERCENTAGE: return new PercentageStrategy();
            default: return new EqualStrategy();
        }
    }
}
