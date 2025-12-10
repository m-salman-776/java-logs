package Project101.SnakeLadder.EntityImple;


import Project101.SnakeLadder.Entity;

public class Ladder extends Entity {
    public Ladder (int src,int dest) {
        super(src,dest);
    }

    @Override
    public String getId() {
        return "L_" + this.getDestinationPosition();
    }

}
