import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class Common {
    public static void main(String[] args) {
        Map<Integer,Integer> map = new LinkedHashMap<>(1,0.75f,true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                return size() > 1;
            }
        };
        Set<Integer> st = new HashSet<>();
        TreeSet<Integer> sorted = new TreeSet(st);
        Integer a  = sorted.lower(1);
        map.compute(1,(k,v)-> {
            if (v == null) return 0;
            else return v + 1;
        });
        int []arr = new int[]{1,2,34};
//        Arrays.se
        map.merge(1,34,Integer::max);
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        sb.append(2);
        sb.toString();
        Collections.binarySearch(List.of(12,3,4,5),1);
        sb.deleteCharAt(0);
        String str = "fsdfasd";

        Arrays.sort(arr);
        str = String.valueOf(arr);
        List<Map.Entry<Character,Integer>> gro = new ArrayList<>();
        gro.add(Map.entry('c',1));
        Map.entry('1',1);
        map.merge(1,1,Integer::sum);
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Stack<Integer> count = new Stack<>();
        executor.scheduleAtFixedRate(()->{
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formatedDateTime = dateTimeFormatter.format(dateTime);
            System.out.println(formatedDateTime);
        },0,1, TimeUnit.SECONDS);
        System.out.println("DONE");
    }
}
