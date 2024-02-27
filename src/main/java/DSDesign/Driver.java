package DSDesign;

import java.util.List;

public class Driver {
    public static void main(String[]args){
        TrieDs trieDs = new TrieDs();
        String [] inputArray = new String[]{
                "abc@gmail.com",
                "abc@gmail.com",
                "abb@gmail.com",
                "abc@gmail.com",
                "xyz@yahoo.com",
                "xyy@gmail.com"};
        for (String input : inputArray){
            trieDs.insert(input);
        }
        List<String> uniqueIds = trieDs.getUniqueId();
        for (String id : uniqueIds){
            System.out.println(id);
        }
        System.out.println("DONE");
    }
}
