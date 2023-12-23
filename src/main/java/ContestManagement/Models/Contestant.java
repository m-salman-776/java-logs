package ContestManagement.Models;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Getter
public class Contestant {
    private long id;
    private String name;
    private Department department;
    private Set<Question> solvedProblems = new HashSet<>();
    private Map<Long, Integer> quesId2Time = new HashMap<>(); // ques->time

    public Contestant(String name , Department department){
        this.department = department;
        this.name = name;
    }

    public Set<Question> getSolvedProblems() {
        return new HashSet<>(solvedProblems);
    }

    public void solveProblem(Question question, int timeTaken) {
        solvedProblems.add(question);
        quesId2Time.put(question.getId(), timeTaken);
    }

    public int getTimeTakenForProblem(Question question) {
        return quesId2Time.getOrDefault(question.getId(), -1);
    }


}