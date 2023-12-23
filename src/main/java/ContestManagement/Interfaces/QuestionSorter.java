package ContestManagement.Interfaces;


import ContestManagement.Models.Question;

import java.util.List;

// Interface for problem sorters
public interface QuestionSorter {
    void sort(List<Question> questions);
}
