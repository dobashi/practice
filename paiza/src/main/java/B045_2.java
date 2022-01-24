import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class B045_2 {
    static final int MAX = 99;

    private static Point pair(int x) {
        return new Point(x, new Random().nextInt(MAX - x));
    }

    private static Set<Point> findNext(Set<Point> l) {
        for(int i=99; i>=0; i--){

        }
        Point p = null;
        do {
            p = pair(new Random().nextInt(MAX));
//            log(p.toString());
        } while (l.contains(p));
        l.add(p);
        return l;
    }

    private static String f(Point p, String mark) {
        return p.x + " " + mark + " " + p.y + " =";
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int add = s.nextInt();
        int sub = s.nextInt();
        Set<Point> addSet = new HashSet<>();
        Set<Point> subSet = new HashSet<>();
        Stream<String> l = Stream.of("25 + 5 =",
                "89 + 10 =",
                "3 + 46 =",
                "82 + 3 =",
                "1 + 84 =",
                "45 - 16 =",
                "70 - 6 =",
                "27 - 26 =");
        l.forEach(x -> log(x));
    }

    private static void log(String s) {
        System.out.println(s);
    }
}
