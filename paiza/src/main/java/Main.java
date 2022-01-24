import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n = s.nextInt();

        n = n * n * 6;
        log(str(n));
    }
    private static void log(String s) { System.out.println(s); }
    private static String str(int i) { return Integer.toString(i); }
}
