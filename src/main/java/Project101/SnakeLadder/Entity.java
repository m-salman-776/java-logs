package Project101.SnakeLadder;

public abstract class Entity {
    int sourcePosition;
    int destinationPosition;
    public Entity(int src,int dest){
        this.sourcePosition = src;
        this.destinationPosition = dest;
    }
    protected int getSourcePosition(){
        return this.sourcePosition;
    }
    protected int getDestinationPosition(){
        return this.destinationPosition;
    }
    public abstract String getId();
}
