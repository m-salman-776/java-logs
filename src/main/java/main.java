import java.util.*;

public class main {
    static Map<String,Integer> mp;
    public static int makeNumber(String num){
        String [] words = num.split(" ");
        Set<String> st = new HashSet<>();
        st.add("million");
        st.add("thousand");
        st.add("hundred");
        int val = 0;
        int ans = 0;
        for (String s : words){
            int intVal = mp.get(s);
            if (st.contains(s)){
               val *= intVal;
               ans += val;
               val = 0;
            }else{
                val += intVal;
            }
        }
        ans += val;
        return ans;
    }
    public static void main(String []args){
        mp = new HashMap<>();

        int size = 10;
        String str[] = new String[size];
        int arr[][] = new int[size][2];
        for (int i=0;i<size;i++){
            int val = makeNumber(str[i]);
            arr[i] = new int[]{val,i};
        }
        Arrays.sort(arr,(a,b)->Integer.compare(a[0],b[0]));
        for (int []e : arr){
            System.out.print(str[e[1]]+" ");
        }
        System.out.println("DINE");
    }
}

/*
zero->0
* one,two,three,four,five,six,seven,eight,nine
* ten,eleven,twelve,thirteen,fourteen,fifteen,sixteen,seventeen,eighteen,nineteen
* twenty,thirty,forty,fifty,sixty,seventy,eighty,ninty
* hundred,thousand
* million
*

five hundred thousand
[one, million, thirty, five, thousand, twelve] ->
num = 12
seventy five",
"two hundred forty one",
"three thousand",
* */
