package ContestManagement.Implemations;


import ContestManagement.Interfaces.ScoringStrategy;
import ContestManagement.Models.Question;

public class DefaultScoringStrategy implements ScoringStrategy {
    @Override
    public int calculateScore(Question question, int timeTaken) {
        return question.getScore();
    }
}

