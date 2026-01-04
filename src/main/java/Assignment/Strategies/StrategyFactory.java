package Assignment.Strategies;

import Assignment.StrategyType;

public class StrategyFactory {
    public static EvaluationStrategy getEvaluationStrategy(StrategyType type){
        switch (type) {
            case FIFTY_PER: new FiftyPercentStrategy();
        }
        return new FiftyPercentStrategy();
    }
}
