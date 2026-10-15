/*
* Given an array of intervals where intervals[i] = [start, end],
* merge all overlapping intervals,
* and return an array of the non-overlapping intervals
* that cover all the intervals in the input.
*
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
*
*
* [1,6] []
[1,3] [2,6]
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
*
* */

/*
* Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.



Example 1:

Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
Example 2:

Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]

]
*
*
*
* longest palidromic sub
*
* ababd
*
*
*
* */

import lombok.AllArgsConstructor;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.*;

//Input
//T 0, A, 4, UP, 14
//T 7, C, 5, UP, 8
public class Common {
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask ts = new TimerTask() {
            @Override
            public void run() {
                System.out.print("DINE");
            }
        };
        timer.schedule(ts, 12);
    }
}
