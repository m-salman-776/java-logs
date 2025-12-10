import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.*;

class Solution {
    public static String[] solution(String[][] paragraphs, int width) {
        List<String> ans = new ArrayList<>();
        for (String[] paragraph : paragraphs) {
            int pidx = 0;
            while (pidx < paragraph.length) {
                List<String> words = new ArrayList<>();
                while (pidx < paragraph.length) {
                    String nextWord = paragraph[pidx];
                    int wlength = 0;
                    for (String w : words) {
                        wlength += w.length();
                    }
                    int spaces = wlength + Math.max(0, words.size());
                    int newlen = spaces + (words.isEmpty() ? 0 : 1) + nextWord.length();

                    if (newlen <= width) {
                        words.add(nextWord);
                    } else if (words.isEmpty()) {
                        words.add(nextWord);
                    } else {
                        break;
                    }
                    pidx++;
                }
                int len = 0;
                for (String w : words) {
                    len += w.length();
                }
                len += Math.max(0, words.size() - 1); // Add 1 space for each gap

                int remaining = width - len;

                int spacesBefore;
                int spacesAfter;

                if (remaining == 0) {
                    spacesBefore = 0;
                    spacesAfter = 0;
                } else if (remaining % 2 == 0) {
                    spacesBefore = remaining / 2;
                    spacesAfter = remaining / 2;
                } else {
                    spacesBefore = remaining / 2;
                    spacesAfter = remaining / 2 + 1;
                }
                StringBuilder lineBuilder = new StringBuilder();
                lineBuilder.append(" ".repeat(spacesBefore));
                lineBuilder.append(String.join(" ", words));
                lineBuilder.append(" ".repeat(spacesAfter));

                ans.add(lineBuilder.toString());
            }
        }
        String borderLine = "*".repeat(width + 2);
        List<String> finalResult = new ArrayList<>();
        finalResult.add(borderLine);
        for (String line : ans) {
            finalResult.add("*" + line + "*");
        }
        finalResult.add(borderLine);
        return finalResult.toArray(new String[0]);
    }
    static Map<Integer,List<Integer>> graph  = new HashMap<>();
    static Set<Integer> vis = new HashSet<>();
    static List<Integer> ans = new ArrayList<>();
    public static void dfs(int src,int parent){
        vis.add(src);
        ans.add(src);
        for (int child : graph.get(src)){
            if (child == parent || vis.contains(child)) continue;
            dfs(child,src);
        }
    }
    public static List<Integer> solve(int [][] path){
        Map<Integer,Integer> indeg = new HashMap<>();
        for (int [] e : path){
            int a = e[0];
            int b= e[1];
            graph.putIfAbsent(a,new ArrayList<>());
            graph.putIfAbsent(b,new ArrayList<>());
            graph.get(a).add(b);
            graph.get(b).add(a);
            indeg.put(a,indeg.getOrDefault(a,0)+1);
            indeg.put(b,indeg.getOrDefault(b,0)+1);
        }
        int start = -1;
        for (int key : indeg.keySet()){
            if (indeg.get(key) == 1){
                start = key;
                break;
            }
        }
        dfs(start,-1);
        return ans;
    }

    public static void main(String []args){
        String[][] arr = new String[][]{{"hello", "world"}, {"How",
                "areYou", "doing"}, {"'Please look", "and align", "to center"}};
        int width = 16;
        String [] ans = solution(arr,width);
        List<Integer> path = solve(new int[][]{{3,5},{1,4},{2,4},{1,5}});
        System.out.println("d");
    }
}