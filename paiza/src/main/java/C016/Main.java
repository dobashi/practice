package C016;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap() {{
            put("A", "4");
            put("E", "3");
            put("G", "6");
            put("I", "1");
            put("O", "0");
            put("S", "5");
            put("Z", "2");
        }};
        Scanner s = new Scanner(System.in);
        String str = s.next();
        for (Map.Entry<String, String> e : map.entrySet()) {
            str = str.replaceAll(e.getKey(), e.getValue());
        }

        log(str);
    }

    private static void log(String s) { System.out.println(s); }
//    private static String str(int i) { return Integer.toString(i); }
}