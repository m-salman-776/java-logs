package utility;

import java.util.Random;

public class RandomNumberGenerator {
    private static Random random;
    private static float min,max;
    private static void randomNumberGenerator(){
        random = new Random();
        min = 100;
        max = 220;
    }
    public static float getRandomFloat(){
        if (random == null) randomNumberGenerator();
        return min + random.nextFloat() * (max - min);
    }
    public static float getRandomFloat(float min,float max){
        if (random == null) randomNumberGenerator();
        return min + random.nextFloat() * (max - min);
    }
    public static int getRandomInt(){
        return (int) (min + random.nextInt() * (max - min));
    }
}
