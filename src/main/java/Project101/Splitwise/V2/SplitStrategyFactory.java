package Project101.Splitwise.V2;

import Project101.Splitwise.code.SplitType;

public class SplitStrategyFactory {
    public static SplitStrategy getStrategy(SplitType splitType){
        switch (splitType){
            default: return  new EqualSplit();
        }
    }
}
