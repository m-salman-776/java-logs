package LLD.ConsitentHashing;

import java.util.HashMap;
import java.util.Map;

public class Main {

    //0 0 0 0 0
    //0 0 0 0 0
    //0 0 0 0 0
    //0 0 0 0 0
    //1 0 0 1 1
    static int grid[][]; // assume atleast once ell
    static int move [] = new int[]{0,1,0,-1,0};
    static void  dfs(int x, int y){
        if (x < 0 || x >= grid.length || y < 0 || y >= grid[0].length || grid[x][y] != 1){
            return;
        }
        grid[x][y] = 0;
        for (int i=0;i<4;i++){
            int newX = x + move[i];
            int newY = y + move[i+1];
            dfs(newX,newY);
        }
    }

//    int [] multiply(char arr[], char digit,int size,int start){
//        int result [] = new int[size];
//        for (char c : arr){
//
//        }
//    }

    static void calculate(String n1,String n2){
        int len1 = n1.length();
        int len2 = n2.length();

        char num1 [] = n1.toCharArray();
        char num2[] = n2.toCharArray();
        int []ans = new int[len1+len2];

        // 123
        // 456

        for (int i=len2-1;i >= 0; i--){
            for (int j=len1-1 ; j >= 0; j--){
              int prod = (num1[j]-'0') * (num2[i]-'0');
              ans[i+j] += prod;
              ans[i+j+1] += ans[i+j] % 10;
              ans[i+j] =  ans[i+j] / 10;
              System.out.print("DONE");
            }
        }
        for (int a : ans){
            System.out.print(a);
        }

    }

    static void find(int []arr){
//        int prefix = 0;
//        for (int i=0;i<arr.length;i++){
//            if (arr[i] == 0) arr[i] = -1;
//        }
//
//        Map<Integer,Integer> mp = new HashMap<>();
//        int ans = 0;
//        for (int i=0;i<arr.length;i++){
//            prefix += arr[i];
//            if (mp.containsKey(0-prefix)) {
//                ans = Math.max(ans, i - mp.get(-prefix) + );
//            }else {
//                mp.put(prefix,i);
//            }
//        }
//        System.out.print(ans);
    }



    // 56088
    public static void main(String []args){

        find(new int[]{0, 0, 1, 0, 1, 0, 0});

//        calculate("123","456");
//        calculate("122","34");

        System.out.print("DONE");
//        int ans = 0;
//        grid = new int[][]{{1, 1, 0, 0, 0},
//                {1, 0, 0, 0, 0},
//            {
//                1, 0, 0, 1, 1
//            },
//            {
//                0, 0, 0, 0, 0
//            },
//            {
//                1, 0, 0, 1, 1
//            }
//        };
//        for (int i=0;i<grid.length;i++){
//            for(int j=0;j<grid[0].length;j++){
//                if (grid[i][j] == 1){
//                    dfs(i,j);
//                    ans += 1;
//                }
//            }
//        }
//        System.out.print(ans);
//        ConsistentHashing consistentHashing = new ConsistentHashing(12);
//        // Adding servers
//        consistentHashing.addServer("Server1");
//        consistentHashing.addServer("Server2");
//        consistentHashing.addServer("Server3");
//        // Getting server for a key
//        String key = "someKey";
//        String server = consistentHashing.getServer(key);
//        System.out.println("Key: " + key + " is mapped to Server: " + server);
//
//        // Removing a server
//        consistentHashing.removeServer("Server2");
//
//        // Getting server for the same key after removing a server
//        server = consistentHashing.getServer(key);
//        System.out.println("Key: " + key + " is mapped to Server: " + server);
    }
}
