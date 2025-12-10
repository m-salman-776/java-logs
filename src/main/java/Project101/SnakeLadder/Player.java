package Project101.SnakeLadder;

import lombok.Getter;
@Getter
public class Player {
    long id;
    private String name;
    private int position;
    public Player(String name){
        this.name = name;
        this.position = 0;
    }
    public void setPosition(int position){
        this.position = position;
    }
}
