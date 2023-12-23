package ContestManagement.Services;


import ContestManagement.Models.Question;
import ContestManagement.Repositories.QuestionRepository;

import java.util.List;

public class QuestionLibrary {
    private QuestionRepository questionRepository;

    public QuestionLibrary(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void addQuestionToLibrary(Question question) {
        questionRepository.addQuestion(question);
    }

    public List<Question> getAllQuestionsFromLibrary() {
        return questionRepository.getAllQuestions();
    }
}