package Project101.cricBuzz;


import Project101.cricBuzz.Common.BallType;
import Project101.cricBuzz.Common.RunType;
import Project101.cricBuzz.Person.Player;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Ball {
    private int overNumber;
    public BallType ballType;
    private Player bowledBy;
    private RunType runType;
    private Player facedBy;
    private Wicket wicket;
    private Commentary commentary;
    public Ball(int overNumber,Player bowledBy,Player facedBy){
        this.bowledBy = bowledBy;
        this.facedBy = facedBy;
        this.overNumber = overNumber;
    }
}

//public class Ball {
//    private Player balledBy; private Player playedBy; private BallType type;
//    private Wicket wicket;
//    private List<Run> runs;
//    private Commentary commentary;
//}