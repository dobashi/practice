package B012;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int count = s.nextInt();
        for (int i = 0; i < count; i++) {
            String card = s.next();
            int even = sumTwice(parse(card, 0));
            int oddBefore = parse(card, 1).stream().mapToInt(x -> x).sum(); // 最後の桁はX
            //log(even +":"+ oddBefore);
            // even + oddが10で割り切れるということは、  10 - (even + oddBefore)%10 = x
            int result = (10 - (even + oddBefore) % 10) %10;
            log(result);
        }

    }

    static List<Integer> parse(String data, int init) {
        List<Integer> list = new ArrayList<>(8);
        for (int i = init; i < 15; i += 2) { // 最後の桁はX
            list.add(Integer.parseInt(String.valueOf(data.charAt(i))));
        }
        return list;
    }

    static int sumTwice(List<Integer> src) {
        return src.stream().mapToInt(i -> twice(i)).sum();
    }

    // 2倍する。ただし2倍したあと2桁の数字になるものは、1の位と10の位の数を足して1桁の数字にする
    static int twice(int i) {
        if (i < 5) return i * 2;
        return (i * 2) % 10 + 1; // 10の位は常に1なので10で割ったあまりに1を足す
    }

    // 半分にする。ただし奇数のときは1引いて10足してから2で割る。
    static int half(int i) {
        if (i % 2 == 0) return i / 2;
        return (i + 9) / 2;
    }
    private static final void log(int i) {
        log(Integer.toString(i));
    }

    private static final void log(String s) {
        System.out.println(s);
    }
}
