package Project101.Cache;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Driver {
    static  int arr[];
    static  int dp[];

    static int findSum(int idx){
        if (idx >= arr.length) return 0;

        if (dp[idx] != -1) return dp[idx];

        int opt1 = 0 , opt2 = 0 , opt3 = 0;

        opt1 = arr[idx]-findSum(idx+1);

        if (idx+1 < arr.length){
            opt2 =   arr[idx] + arr[idx+1] - findSum(idx+2);
        }

        if (idx + 2 < arr.length){
            opt3 =  arr[idx] + arr[idx+1] + arr[idx+2] - findSum(idx+3);
        }

        int max_ = Math.max(opt1,Math.max(opt2,opt3));
        return dp[idx] = max_;
    }

    static  void findScore(int totalSum, int diff){
        for (int first = 1 ; first <= totalSum; first ++){
            if (totalSum == first + Math.abs(diff)){
                int one = first;
                int other = totalSum - one;
                int player1 = 0 , player2 = 0;
                if (diff > 0){
                    player1 = Math.max(one,other);
                    player2 = Math.min(one,other);
                }else {
                    player1 = Math.min(one,other);
                    player2 = Math.max(one,other);
                }
                System.out.println("Player 1 : " + player1 + " Player 2 " +player2);
            }
        }
    }
// ............
    public static void main(String []args){
//        scores = new HashMap<>();
        arr = new int[]{1, 2, 3, 1, 10, 4, 5, 7, 11, 3, 1, 100};
//        arr = new int[]{1, 2, 3, 1, 10};

        int s = (int) Math.random();
        dp = new int[arr.length+1];
        Arrays.fill(dp,-1);
        int totalSum = 0;
        for (int a : arr){
            totalSum += a;
        }
        int diff = findSum(0);

        if (diff > 0){
            System.out.print("Player 1 is winner " + diff);
        }else if (diff < 0){
            System.out.print("Player 2 is winner " + diff);
        }else {
            System.out.println("DRAW");
        }
        System.out.println();
        System.out.println("DONE");
    }
}

/*
* example 1 - {1, 2, 3}
player 1 can take all numbers and will have sum as 6. second play is left with no number, so score 0.
player 1 will win the game

example 2 - {1, 2, 3, 1, 10}
first turn for player 1: 1
first turn for player 2: 2+3+1=6
second turn for player 1: 10

total score for player 1= 1+10=11
total score for player 2=6

11>6, so player 1 wins the game
* */
