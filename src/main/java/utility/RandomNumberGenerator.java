package utility;

import java.util.Random;

public class RandomNumberGenerator {
    private static Random random;
    private static float min,max;
    private static void randomNumberGenerator(){
        random = new Random();
        min = 100.0f;
        max = 220.0f;
    }
    public static float getRandomFloat(){
        if (random == null) randomNumberGenerator();
        return min + random.nextFloat() * (max - min);
    }
    public static float getRandomFloat(float min,float max){
        if (random == null) randomNumberGenerator();
        return min + random.nextFloat() * (max - min);
    }
}
