package ContestManagement.Repositories;


import ContestManagement.Models.Question;

import java.util.ArrayList;
import java.util.List;

public class QuestionRepository {
    private List<Question> questionList = new ArrayList<>();

    public void addQuestion(Question question) {
        questionList.add(question);
    }

    public List<Question> getAllQuestions() {
        return new ArrayList<>(questionList);
    }
}