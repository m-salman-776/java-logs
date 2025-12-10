package Project101.cricBuzz;

import java.util.Date;
import java.util.List;

public class Tournament {
    String name;
    long id;
    Date startDate;
    Date endDate;
    List<Match> matches;
    public Tournament(String name,Date startDate,Date endDate){
        this.name= name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
