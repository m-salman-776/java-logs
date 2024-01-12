package LLD.cricBuzz.Person;
import LLD.cricBuzz.Ball;
import LLD.cricBuzz.Commentary;
import LLD.cricBuzz.Common.PersonType;
import LLD.cricBuzz.Common.WicketType;
import LLD.cricBuzz.Wicket;

import java.time.Instant;
import java.util.Date;

public class Commentator extends Person{
    public Commentator(String name, PersonType personType) {
        super(name, personType);
    }
    public void addBall(){
    }
    private Ball makeBall(int overNumber,Player bowledBy,Player facedBy,boolean wicketBall){ /// comes from UI
        Ball ball = new Ball(overNumber,bowledBy,facedBy);
        if (wicketBall){
            Wicket wicket = getWicketObject();
            ball.setWicket(wicket);
        }
        ball.setCommentary(getCommentaryObject());
        return ball;
    }
    private Commentary getCommentaryObject(){
        Commentary commentary = new Commentary();
        commentary.setCommentator(this);
        commentary.setCreatedAt(Date.from(Instant.now()));
        commentary.setText("Wallah you did this");
        return commentary;
    }
    private Wicket getWicketObject(){
        WicketType wicketType = WicketType.BOWLED;// any input based on ui
        Player out = new Player("Some name");
        Player outBy = new Player("Other name");
        Wicket wicket = new Wicket(wicketType,out);
        switch (wicketType){
            case CATCH : wicket.setCaughtBy(outBy);break;
            case RUN_OUT:wicket.setRunOutBy(outBy);break;
            case STUMP:wicket.setStumpedBy(outBy);break;
            case BOWLED :wicket.setBowledBy(outBy);break;
            default:
        }

        return wicket;
    }
}
