import java.awt.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class B045 {
    static final int MAX = 99;

    private static Point pair(int x) {
        return new Point(x, new Random().nextInt(MAX - x));
    }

    private static Point pairS(int x) {
        if(x==0) return new Point(0,0);
        return new Point(x, new Random().nextInt(x));
    }

    private static Set<Point> findNext(Set<Point> l) {
        Point p = null;
        do  p = pair(new Random().nextInt(MAX));  while (l.contains(p));
        l.add(p);
        return l;
    }

    private static Set<Point> findNextS(Set<Point> l) {
        Point p = null;
        do  p = pairS(new Random().nextInt(MAX));  while (l.contains(p));
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
        for (int i = 0; i < add; i++) findNext(addSet);
        for (int i = 0; i < sub; i++) findNextS(subSet);
        addSet.forEach(x -> log(f(x, "+")));
        subSet.forEach(x -> log(f(x, "-")));
    }

    private static void log(String s) {
        System.out.println(s);
    }
}
