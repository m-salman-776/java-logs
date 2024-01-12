package LLD.SnakeLadder.EntityImple;


import LLD.SnakeLadder.Entity;

public class Snake extends Entity {
    public Snake(int src, int dest) {
        super(src, dest);
    }

    @Override
    public String getId() {
        return "S_" + this.getDestinationPosition();
    }
}
