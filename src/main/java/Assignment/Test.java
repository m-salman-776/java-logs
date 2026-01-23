package Assignment;

import java.util.*;

class AlertWithFrequency {
    String type;
    int freq;
    public AlertWithFrequency (String type,int freq){
        this.type = type;
        this.freq = freq;
    }
}
public class Test {
    static List<String> solve(List<String> input) {

        Map<String,Integer> map = new HashMap<>();
        for (String alertType : input){
            map.put(alertType,map.getOrDefault(alertType,0)+1);

        }
        List<AlertWithFrequency> alterList = new ArrayList<>();
        for (String key : map.keySet()){
            int val = map.get(key);
            alterList.add(new AlertWithFrequency(key,val));
        }
        Collections.sort(alterList,(a, b) -> {
            if (a.freq == b.freq) {
                return a.type.compareTo(b.type);
            }
            return Integer.compare(a.freq,b.freq);
        });

        List<String> ans = new ArrayList();

        for (AlertWithFrequency alert : alterList){
            int freq = alert.freq;
            for (int i=0; i< freq; i++){
                ans.add(alert.type);
            }
        }
        return ans;
    }
    public static void main(String[] args) {
        List<String> input = List.of("A","B","C","A","A","D");
        System.out.println(solve(input));

    }
}