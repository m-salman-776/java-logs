package Assignment;

import Assignment.Strategies.Feature;
import Assignment.Strategies.StrategyFactory;

import java.util.HashMap;
import java.util.Map;


public class SentryService {
    Map<String, Feature> feature = new HashMap<>();
    int featureID = 0;
    public SentryService(){

    }
    public synchronized boolean createFeatureFlag(String name){
        this.featureID += 1;
        Feature feature1 = new Feature(this.featureID,name,Status.BLOCKED);
        feature.put(name,feature1);
        return true;
    }
    public synchronized boolean updateFlagStatus(String name,Status status){
        Feature feature1 = this.feature.get(name);
        feature1.status = status;
        return true;
    }
    public boolean evaluate(String featureName, int userId,StrategyType type){
        if(!feature.containsKey(featureName)){
            return false;
        }
        Feature feature1 = this.feature.get(featureName);
        return StrategyFactory.getEvaluationStrategy(type).evaluate(feature1,userId);
    }
}
