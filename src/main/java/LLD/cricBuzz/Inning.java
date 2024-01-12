package LLD.cricBuzz;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
public class Inning {
    private int number;
    private Date startTime;
    private List<Over> overs;
    public Inning(int number){
        this.number = number;
        overs = new ArrayList<>();
    }
    public boolean addOver(Over over){
        this.overs.add(over);
        return true;
    }
}
