package ContestManagement.Interfaces;


import ContestManagement.Models.Question;

public interface QuestionFilter {
    boolean filter(Question question);
}
