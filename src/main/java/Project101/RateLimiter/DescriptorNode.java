package Project101.RateLimiter;
//
//import java.util.HashMap;
//import java.util.Map;
//
public class DescriptorNode {
//    // 1. Who am I? (e.g., key="user", value="bkthomps")
//    private String key;
//    private String value; // If null, acts as Wildcard (*)
//
//    // 2. Recursive Children (The Nesting)
//    // Map allows O(1) lookup: "key:value" -> Node
//    private Map<String, DescriptorNode> children = new HashMap<>();
//
//    // 3. The Limit Policy (Optional - only leaves or specific nodes have this)
//    private RateLimitPolicy policy;
//
//    public void addChild(DescriptorNode node) {
//        // Optimization: Create a composite key for the map
//        children.put(node.key + ":" + node.value, node);
//    }
//
//    public DescriptorNode getChild(String key, String value) {
//        return children.get(key + ":" + value);
//    }
}