package Project101.SnakeLadder;

import java.util.Random;

public class Dice {
    private final int maxValue;
    private final Random random;
    public Dice(int maxValue){
        this.maxValue = maxValue;
        random = new Random();
    }
    public int rollUp(){
       return random.nextInt(maxValue)+1; // [0....maxVal) + 1 => [1....maxVal]
    }
}
