package Project101.Splitwise.SplitStrategy;
import Project101.Splitwise.SplitType;

public class SplitStrategyFactory {
    public static SplitStrategy getStrategy(SplitType splitType){
        switch (splitType){
            default: return  new EqualSplit();
        }
    }
}
