package ContestManagement.Services;


import ContestManagement.Interfaces.QuestionFilter;
import ContestManagement.Interfaces.QuestionSorter;
import ContestManagement.Models.Question;
import ContestManagement.Repositories.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

public class QuestionFilterAndSortService {
    private List<Question> questions;// not required
    private QuestionRepository questionRepository ;

    public QuestionFilterAndSortService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> filter(List<QuestionFilter> filters, List<Question>questionList){
        if (filters == null || filters.isEmpty()){
            return questionList;
        }

        for (QuestionFilter filter : filters) {
            List<Question> updatedFilteredQuestions = new ArrayList<>();

            for (Question question : questionList) {
                if (filter.filter(question)) {
                    updatedFilteredQuestions.add(question);
                }
            }

            questionList = updatedFilteredQuestions;
        }
        return questionList;
    }

    public List<Question> sort(List<QuestionSorter> sorters, List<Question>questions){
        if (sorters == null || sorters.isEmpty()){
            return questions;
        }
        for (QuestionSorter sorter : sorters) {
            sorter.sort(questions);
        }
        return questions;
    }
    public List<Question> filterAndSort(List<QuestionFilter> filters, List<QuestionSorter> sorters) {
        List<Question> questionList = new ArrayList<>(this.questionRepository.getAllQuestions());

        List<Question> filteredQuestions = this.filter(filters,questionList);

        List<Question> sortedList = this.sort(sorters,filteredQuestions);

        return sortedList;
    }
}

