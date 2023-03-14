package Assignment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Assignment {
    public static void main(String []args){
//        Scanner scanner = new Scanner(System.in);
//        int days = scanner.nextInt();
//        ArrayList<String> arr = new ArrayList<>();
//        for (int i=0;i<days;i++){
//            String t = scanner.next();
//            arr.add(t);
//        }
        ArrayList<String> arr = new ArrayList<>(Arrays.asList("FatOil", "FiberSpinach", "CarbRice", "FatCheese", "FiberBeans"));
        solve(arr);
    }
    public static void solve(ArrayList<String> ingredients){
        int size = ingredients.size();
        ArrayList<String> ans = new ArrayList<>(Arrays.asList("#","#"));
        HashMap<String,Integer> expiry = new HashMap<>();
        expiry.put("Fat",1); expiry.put("Carb",2);expiry.put("Protein",3);expiry.put("Fiber",3);expiry.put("Seasoning",4);
        boolean []used = new boolean[size];
        for (int day = 4 ; day<size;day++){
            int ingerCount = 0;
            String tempans = "";
            HashMap<String,Integer> catCount = new HashMap<>();
            ArrayList<Integer> useIdx = new ArrayList<>();
            boolean added = false;
            boolean catLimit = false;
        for(int idx = 0 ; idx <= day; idx ++) {
            String ingredient = ingredients.get(idx);
            String category = extractCategory(ingredient);
            if (used[idx]) continue;
            if (idx + expiry.get(category) >= day) {
                if (catCount.containsKey(category)) {
                    catCount.put(category, catCount.get(category) + 1);
                } else {
                    catCount.put(category, 1);
                }
                ingerCount += 1;
                if (catCount.get(category) == 2) catLimit = true;
                useIdx.add(idx);
                tempans += ingredients.get(idx) + ":";
                if (ingerCount == 3 &&  catLimit) {
                    ingerCount = 0;
                    catCount.clear();
//                    ans.add(tempans.substr(0,tempans.size()-2));
                    added = true;
                    ans.add(tempans);
                    for (int i : useIdx) used[i] = true;
                }
            }
        }
        if (added == false) {
            ans.add("#");
        }
    }
        for (String t : ans) {
            System.out.print(t + " ");
        }
    }
    static boolean hasPrefix(String prefix, String word){
        for (int idx=0;idx < prefix.length();idx++){
            if (prefix.charAt(idx) != word.charAt(idx)) return false;
        }
        return true;
    }
    static String extractCategory(String word){
        ArrayList <String>prefixes = new ArrayList<>(Arrays.asList("Fiber","Fat","Carb","Protein","Seasoning"));
        for (String x : prefixes){
            if (hasPrefix(x,word)) return x;
        }
        return "";

    }
}

//#include<bits/stdc++.h>
//        using namespace std;
//        bool hasPrefix(string prefix, string word){
//        for (int idx=0;idx < prefix.size();idx++){
//        if (prefix[idx] != word[idx]) return false;
//        }
//        return true;
//        }
//        string extractCategory(string word){
//        vector<string> prefixes = {"Fiber","Fat","Carb","Protein","Seasoning"};
//        for (auto x : prefixes){
//        if (hasPrefix(x,word)) return x;
//        }
//        return "";
//
//        }
//        void solve(){
//        int days ; std::cin >> days;
//        vector<string>ingredient(days);
//        vector<bool>used(days,false);
//        for (int i=0;i<days;i++){
//        cin>>ingredient[i];
//        }
//        unordered_map<string,int> expiry;
//        expiry["Fat"] = 1;
//        expiry["Carb"] = 2;
//        expiry["Protein"] = 3;
//        expiry["Fiber"] = 3;
//        expiry["Seasoning"] = 4;
//        vector<string>ans(days);
//        ans[0] = "#";
//        ans[1] = "#";
//        for (int day = 2 ;day < days;day++){
//        int ingerCount = 0;
//        string tempans = "";
//        for(int idx = 0 ; idx <= day; idx ++){
//        unordered_map<string,int>catCount;
//        vector<int>useIdx;
//        string categ = extractCategory(ingredient[idx]);
//        if (used[idx]) continue;
//        if (idx + expiry[categ] <= day){
//        catCount[categ] += 1;
//        ingerCount += 1;
//        useIdx.push_back(idx);
//        tempans += ingredient[idx] + ":";
//        if (ingerCount == 3 and catCount[categ] == 0){
//        ans.push_back(tempans.substr(0,tempans.size()-2));
//        for (int i : useIdx) used[i] = true;
//        }
//        }
//        }
//        }
//        for (auto x : ans) cout<<x<<" ";
//        }
//        int main() {
//        solve();
//        return 0;
//        }
