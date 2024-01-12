package LLD.cricBuzz.Person;


import LLD.cricBuzz.Common.PersonType;
import LLD.cricBuzz.Common.PlayerType;
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
