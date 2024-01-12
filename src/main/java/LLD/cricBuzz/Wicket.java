package LLD.cricBuzz;



import LLD.cricBuzz.Common.WicketType;
import LLD.cricBuzz.Person.Player;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Wicket {
    private WicketType wicketType;
    private Player playerOut;
    private Player caughtBy;
    private Player runOutBy;
    private Player stumpedBy;
    private Player bowledBy;
    public Wicket(WicketType wicketType,Player out){
        this.wicketType = wicketType;
        this.playerOut = out;
    }
}
