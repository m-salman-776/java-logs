package Project101.cricBuzz.Person;


import Project101.cricBuzz.Common.PersonType;
import Project101.cricBuzz.Common.PlayerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Person {
    PlayerType playerType;
    public Player(String name){
        super(name, PersonType.PLAYER);
    }
}
