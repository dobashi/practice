package C064;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int m = s.nextInt();
        int n = s.nextInt();

        // foods
        int[] calories = new int[m];
        for(int i=0; i<m; i++){
            calories[i] = s.nextInt(); // 100gあたりのカロリー
        }

        // person
        for(int i=0; i<n; i++){
            Person p = new Person();
            for(int j=0; j<m; j++){
                int weight = s.nextInt();
                int eatenCalorie = weight * calories[j] / 100;
                p.add(eatenCalorie);
            }
            log(str(p.total()));
        }
    }
    static class Person{
        List<Integer> list = new ArrayList<>();
        void add(int c){
            list.add(c);
        }

        int total(){
            return list.stream().mapToInt(x -> x).sum();
        }
    }
    private static void log(String s) { System.out.println(s); }
    private static String str(int i) { return Integer.toString(i); }
}
