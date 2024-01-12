package LLD.SnakeLadder.EntityImple;


import LLD.SnakeLadder.Entity;

public class Ladder extends Entity {
    public Ladder (int src,int dest) {
        super(src,dest);
    }

    @Override
    public String getId() {
        return "L_" + this.getDestinationPosition();
    }

}
