package LLD.Chess;

import lombok.Getter;

@Getter
public class Position {
    int x;
    int y;
    public Position(int x , int y){
        this.x = x;
        this.y = y;
    }
}
