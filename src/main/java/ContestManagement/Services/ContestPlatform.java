package ContestManagement.Services;


import ContestManagement.Interfaces.QuestionFilter;
import ContestManagement.Interfaces.QuestionSorter;
import ContestManagement.Interfaces.ScoringStrategy;
import ContestManagement.Models.Contestant;
import ContestManagement.Models.Department;
import ContestManagement.Models.Question;

import java.util.*;

public class ContestPlatform {
    // services
    private ContestantService contestantService;
    private QuestionLibrary questionLibrary;
    private QuestionFilterAndSortService questionFilterAndSortService;
    private ScoringStrategy scoringStrategy;


    public ContestPlatform(ScoringStrategy scoringStrategy,
                           ContestantService contestantService,
                           QuestionFilterAndSortService questionFilterAndSortService,
                           QuestionLibrary questionLibrary){
        this.scoringStrategy = scoringStrategy;
        this.contestantService = contestantService;
        this.questionFilterAndSortService = questionFilterAndSortService;
        this.questionLibrary = questionLibrary;
    }

    // Contestant registration

    // TODO Req 1
    public void addQuestionToRepository(Question question) {
        this.questionLibrary.addQuestionToLibrary(question);
    }

    // Retrieve all questions from the repository
    public List<Question> getAllQuestionsFromRepository() {
        return this.questionLibrary.getAllQuestionsFromLibrary();
    }

    public void registerUser(String name , Department department){
        Contestant contestant = new
                Contestant(name,department);
        this.contestantService.addContestant(contestant);
    }

    public void registerUser(Contestant contestant){
        this.contestantService.addContestant(contestant);
    }
    public List<Question> filterAndSort(List<QuestionFilter> questionFilters, List<QuestionSorter> questionSorters){
        List<Question> filteredAndSortedQuestions = questionFilterAndSortService.filterAndSort(questionFilters, questionSorters);
        return filteredAndSortedQuestions;

    }

    // Get the list of problems solved by a contestant
    public Set<Question> getProblemsSolvedByContestant(Contestant contestant) {
        return contestant.getSolvedProblems();
    }

    public void solveProblem(Contestant contestant, Question question, int timeTaken) {
        contestant.solveProblem(question, timeTaken);
        question.markAsSolved(contestant.getId(), timeTaken);
    }

    public int getNumberOfUsersSolvedProblem(Question question) {
        return question.getNumberOfUsersSolved();
    }

    public double getAverageTimeTakenForProblem(Question question) {
        return question.getAverageTimeTaken();
    }


    public int calculateContestantScore(Contestant contestant) {
        int totalScore = 0;

        for (Map.Entry<Long, Integer> entry : contestant.getQuesId2Time().entrySet()) {
            totalScore += calculateProblemScore(entry.getKey(), entry.getValue());
        }

        return totalScore;
    }

    private int calculateProblemScore(long questionId, int timeTaken) {
        for (Question question : questionLibrary.getAllQuestionsFromLibrary()) {
            if (question.getId() == questionId) {
                return scoringStrategy.calculateScore(question, timeTaken);
            }
        }

        return 0;
    }

    public Contestant getCurrentLeader() {

        Contestant currentLeader = this.contestantService.getContestantById(0L);
        if (currentLeader == null){
            // no contestant
//           // TODO
            return null;
        }

        for (Contestant contestant : this.contestantService.getAllContestant()) {
            int contestantScore = calculateContestantScore(contestant);
            int leaderScore = calculateContestantScore(currentLeader);

            if (contestantScore > leaderScore) {
                currentLeader = contestant;
            }
        }

        return currentLeader;
    }
    public List<Question> getTopNLikedProblems(String tag, int n) {
        List<Question> filteredQuestions = new ArrayList<>();

        for (Question question : questionLibrary.getAllQuestionsFromLibrary()) {
            if (question.getTag().equals(tag)) {
                filteredQuestions.add(question);
            }
        }

        filteredQuestions.sort(Comparator.comparingInt(Question::getLikes).reversed());

        List<Question> topNQuestions = new ArrayList<>();
        for (int i = 0; i < Math.min(n, filteredQuestions.size()); i++) {
            topNQuestions.add(filteredQuestions.get(i));
        }

        return topNQuestions;
    }
}
