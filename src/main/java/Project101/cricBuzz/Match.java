package Project101.cricBuzz;



import Project101.cricBuzz.Common.Address;
import Project101.cricBuzz.Person.Commentator;
import Project101.cricBuzz.Person.Referee;
import Project101.cricBuzz.Person.Umpire;

import java.util.*;

public abstract class Match {
    private long matchId;
    private Date starDate;
    private List<Inning> innings;
    private List<Commentator> commentators;
    private List<Umpire> umpires;
    private Team winnerUp;
    private Team runnerUp;
    private Address venue;
    private Referee referee;
    private List<Team> teams;
    public Match(Date starDate,Team team1,Team team2){
        umpires = new LinkedList<>();
        innings = new LinkedList<>();
        commentators = new LinkedList<>();
        this.starDate = starDate;
        this.teams = new ArrayList<>();
        this.teams.addAll(Arrays.asList(team1,team2));
    }
}
