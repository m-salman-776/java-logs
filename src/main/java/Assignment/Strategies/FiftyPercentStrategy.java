package Assignment.Strategies;

public class FiftyPercentStrategy implements EvaluationStrategy {
    @Override
    public boolean evaluate(Feature feature, int userId) {
        return (feature.featureId + userId) % 2 == 0;
    }
}

// [25,25,50]
// [25,75]

// [50]
// [25,25]
//  A , B, C , D
// [25,25,25,25]
//]
// [1,5,86,90] = 100

// 23
// 100




