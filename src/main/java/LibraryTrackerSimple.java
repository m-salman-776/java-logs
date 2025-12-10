import java.util.*;

public class LibraryTrackerSimple {
    // category -> (price -> count), TreeMap keeps prices sorted ascending
    private final Map<String, TreeMap<Integer, Long>> store = new HashMap<>();
    public List<Long> process(List<String[]> operations) {
        List<Long> result = new ArrayList<>();

        for (String[] op : operations) {
            String type = op[0];

            if ("acquisition".equals(type)) {
                String cat = op[1];
                int qty = Integer.parseInt(op[2]);
                int price = Integer.parseInt(op[3]);
                TreeMap<Integer, Long> map = store.computeIfAbsent(cat, k -> new TreeMap<>());
                map.put(price, map.getOrDefault(price, 0L) + qty);

            } else if ("checkout".equals(type)) {
                String cat = op[1];
                int qty = Integer.parseInt(op[2]);
                TreeMap<Integer, Long> map = store.getOrDefault(cat, new TreeMap<>());

                long need = qty;
                long sum = 0L;

                while (need > 0) {
                    Map.Entry<Integer, Long> e = map.firstEntry();
                    // per statement, there will always be enough books to satisfy checkouts
                    if (e == null) break;
                    int price = e.getKey();
                    long avail = e.getValue();

                    long take = Math.min(avail, need);
                    sum += take * (long) price;
                    need -= take;

                    if (take == avail) map.remove(price);
                    else map.put(price, avail - take);
                }

                result.add(sum);

            } else if ("reclassify".equals(type)) {
                String cat = op[1];
                int qty = Integer.parseInt(op[2]);
                int oldP = Integer.parseInt(op[3]);
                int newP = Integer.parseInt(op[4]);

                TreeMap<Integer, Long> map = store.computeIfAbsent(cat, k -> new TreeMap<>());
                long avail = map.getOrDefault(oldP, 0L);
                long after = avail - qty; // guaranteed avail >= qty
                if (after <= 0) map.remove(oldP);
                else map.put(oldP, after);

                map.put(newP, map.getOrDefault(newP, 0L) + qty);
            }
        }

        return result;
    }

    // quick demo
    public static void main(String[] args) {
        String s = "sdfas";
        StringBuilder sb = new StringBuilder();
        sb.reverse().toString();
        LibraryTrackerSimple lt = new LibraryTrackerSimple();
        List<String[]> ops = new ArrayList<>();
        ops.add(new String[]{"acquisition","fiction","3","10"});
        ops.add(new String[]{"acquisition","fiction","2","5"});
        ops.add(new String[]{"checkout","fiction","4"});     // -> 30 (2@5 + 2@10)
        ops.add(new String[]{"reclassify","fiction","1","10","20"});
        ops.add(new String[]{"checkout","fiction","1"});     // -> 10 (remaining cheapest)
        System.out.println(lt.process(ops)); // prints: [30, 10]
    }
}
