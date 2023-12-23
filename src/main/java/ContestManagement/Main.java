package ContestManagement;



import ContestManagement.Implemations.DefaultScoringStrategy;
import ContestManagement.Implemations.DifficultyLevelFilter;
import ContestManagement.Implemations.ScoreSorter;
import ContestManagement.Interfaces.QuestionFilter;
import ContestManagement.Models.Contestant;
import ContestManagement.Models.Department;
import ContestManagement.Models.Difficulty;
import ContestManagement.Models.Question;
import ContestManagement.Repositories.ContestantRepository;
import ContestManagement.Repositories.QuestionRepository;
import ContestManagement.Services.ContestPlatform;
import ContestManagement.Services.ContestantService;
import ContestManagement.Services.QuestionFilterAndSortService;
import ContestManagement.Services.QuestionLibrary;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void test(){
        while (true){

        }
    }
    public static void main(String[] args) {
        test();
        QuestionRepository questionRepository = new QuestionRepository();

        ContestPlatform platform = new ContestPlatform(
                new DefaultScoringStrategy(),
                new ContestantService(new ContestantRepository()),
                new QuestionFilterAndSortService(questionRepository),
                new QuestionLibrary(questionRepository)
        );

        // TODO

        Question question1 = new Question(1, "Sample Question 1", "Description of the sample question.", "Java",
                Difficulty.MEDIUM, 10, 1);
        Question question2 = new Question(1, "Sample Question 2", "Description of the sample question.", "Java",
                Difficulty.EASY, 15, 2);
        Question question3 = new Question(1, "Sample Question 3", "Description of the sample question.", "Java",
                Difficulty.HARD, 20, 3);


        Department department = new Department(12,"Sample Name");
        Contestant contestant = new Contestant("Sample Name",department);

        // add question
        platform.addQuestionToRepository(question1);
        platform.addQuestionToRepository(question2);
        platform.addQuestionToRepository(question3);

        List<Question> questions = platform.getAllQuestionsFromRepository();


        platform.registerUser("name",department);
        platform.registerUser(contestant);

        QuestionFilter questionFilter = new DifficultyLevelFilter(Difficulty.MEDIUM.toString());
        ScoreSorter scoreSorter = new ScoreSorter();
        List<Question> questionList = platform.filterAndSort(Arrays.asList(questionFilter),Arrays.asList(scoreSorter));
//        List<Question> questionList = contestPlatform.filterAndSort(null,null);

        platform.solveProblem(contestant,question1,1234);
        Set<Question> questionSet = platform.getProblemsSolvedByContestant(contestant);

        int numberOfUserSolve = platform.getNumberOfUsersSolvedProblem(question1);
        double avgTimeTaken = platform.getAverageTimeTakenForProblem(question1);

        Contestant currentLeader = platform.getCurrentLeader();

        List<Question> topNLikedProblemsByTag = platform.getTopNLikedProblems("Java",2);
        System.out.println("DONE");

    }
}