package Project101.cricBuzz;

import Project101.cricBuzz.Common.RunType;
import Project101.cricBuzz.Common.WicketType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Over {
    int overNumber;
    private int runScored;
    private int extra;
    private int totalRun;
    private List<Ball> balls;
    private List<WicketType> wickets;
    public Over(int overNumber){
        this.overNumber = overNumber;
    }
    public boolean isMaiden(){
        for (Ball ball : balls){
            if (ball.getRunType() != RunType.NO_RUN){
                return false;
            }
        }
        return true;
    }
}
