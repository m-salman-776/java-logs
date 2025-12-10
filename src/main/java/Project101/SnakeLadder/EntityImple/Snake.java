package Project101.SnakeLadder.EntityImple;


import Project101.SnakeLadder.Entity;

public class Snake extends Entity {
    public Snake(int src, int dest) {
        super(src, dest);
    }

    @Override
    public String getId() {
        return "S_" + this.getDestinationPosition();
    }
}
