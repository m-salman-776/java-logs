package ContestManagement.Implemations;


import ContestManagement.Interfaces.QuestionFilter;
import ContestManagement.Models.Question;

public class DifficultyLevelFilter implements QuestionFilter {
    private String difficultyLevel;
    public DifficultyLevelFilter(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
    @Override
    public boolean filter(Question question) {
        if (question.getDifficulty().toString().equals(difficultyLevel)){
            return true;
        }
        return false;
    }
}