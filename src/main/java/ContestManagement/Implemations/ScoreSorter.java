package ContestManagement.Implemations;


import ContestManagement.Interfaces.QuestionSorter;
import ContestManagement.Models.Question;

import java.util.Comparator;
import java.util.List;

public class ScoreSorter implements QuestionSorter {
    @Override
    public void sort(List<Question> questions) {
        questions.sort(Comparator.comparing(Question::getScore).reversed());
    }
}