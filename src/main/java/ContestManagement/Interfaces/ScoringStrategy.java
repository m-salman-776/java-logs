package ContestManagement.Interfaces;


import ContestManagement.Models.Question;

public interface ScoringStrategy {
    int calculateScore(Question question, int timeTaken);
}
