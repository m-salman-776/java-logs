package ContestManagement.Models;

import lombok.Getter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Getter
public class Question {
    private long id;
    private String name;
    private String description;
    private String tag;
    private Difficulty difficulty;
    private int score;
    private int likes;
    private Set<Long> solvedBy = new HashSet<>();
    private int total ;
    private Map<Long, Integer> solveTimes = new HashMap<>(); // User -> Time taken
    // TODO private solvedTime ;

    public Question(long id, String name, String description, String tag, Difficulty difficulty, int score, int likes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tag = tag;
        this.difficulty = difficulty;
        this.score = score;
        this.likes = likes;
    }
    public void like(){
        this.likes += 1;
    }

    public void markAsSolved(long userId, int timeTaken) {
        solvedBy.add(userId);
        solveTimes.put(userId, timeTaken);
    }

    public int getNumberOfUsersSolved() {
        return solvedBy.size();
    }

    public double getAverageTimeTaken() {
        if (solvedBy.isEmpty()) {
            return 0.0; // Handle division by zero
        }
        int totalTime = 0;

        for (Long userId : solvedBy) {
            totalTime += solveTimes.get(userId);
        }
        return (double) totalTime / solvedBy.size();
    }
}
