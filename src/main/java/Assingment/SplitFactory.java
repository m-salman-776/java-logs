package Assingment;

public class SplitFactory {
    static SplitStrategy getSplitSplitFactory(SplitType type){
        switch (type){
            case EQUAL:return new EqualStrategy();
            case PERCENTAGE:new EqualStrategy();
            case EXACT: return new EqualStrategy();
//            default: TODO
            }
        return new EqualStrategy();
        }
}
