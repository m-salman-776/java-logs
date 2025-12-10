package Project101.SnakeLadder;
import java.util.HashMap;

public class Board {
    private int size;
    private HashMap<Integer,Entity> snakeOrLadder;
    public Board(int size){
        this.size = size;
        snakeOrLadder = new HashMap<>();
    }

    public void addSnakeOrLadder(Entity entity){
        snakeOrLadder.put(entity.getSourcePosition(),entity);
    }
    public Entity getSnakeOrLadder(int position){
        return snakeOrLadder.get(position);
    }
    public boolean checkForSnakeOrLadder(int position){
        return snakeOrLadder.containsKey(position);
    }
    public void printBoard()
    {
        int totalCells = size*size;
        int printCount = 0;
        for(int i=totalCells; i > 0; i--)
        {
            printCount += 1;
            System.out.print(" | " + i + " ");

            if(checkForSnakeOrLadder(i))
                System.out.print(snakeOrLadder.get(i).getId());
            System.out.print(" |");
            if(printCount % size == 0)
                System.out.println();
        }
    }
    public int getTotalCell(){
        return size * size;
    }
}
