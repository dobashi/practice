package D106;

import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int m = s.nextInt();
        int n = s.nextInt();

        log(str(m%n));
    }
    private static void log(String s) { System.out.println(s); }
    private static String str(int i) { return Integer.toString(i); }
}
