package B066;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int count = s.nextInt();
        int n = s.nextInt();
        int m = s.nextInt();
        // model
        List<String> data = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            data.add(s.next());
        }
        ModelParser parser = new ModelParser(n, data);

        // make model Pieces
        int pn = n / m;
        Piece[][] modelPieces = new Piece[pn][pn];
        for (int i = 0; i < pn; i++) {
            for (int j = 0; j < pn; j++) {
                modelPieces[i][j] = parser.make(i, j, m);
            }
        }
        Model model = new Model(modelPieces);

        // read input Pieces
        for (int index = 0; index < count; index++) {
            Color[][] pattern = new Color[m][m];
            for (int i = 0; i < m; i++) {
                String pdata = s.next();
                for (int j = 0; j < m; j++) {
                    pattern[i][j] = Color.valueOf(pdata.charAt(j));
                }
            }
            Piece p = new Piece(pattern);
            out(model.find(p).toString());
        }
    }

    static class Model {
        Piece[][] pieces;
        int n;

        Model(Piece[][] _pieces) {
            pieces = _pieces;
            n = pieces.length;
        }

        // -1: not found, other: rotate count
        FindResult find(Piece p) {
            log("Piece: \n" + p.toString());
            int m = p.pattern.length;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    log("Model[" + i + "][" + j + "]\n" + pieces[i][j].toString());
                    Piece modelPiece = pieces[i][j];
                    if (p.isSame(modelPiece)) {
                        return new FindResult(m, i, j, 0);
                    }
                    // 色文字数が違うならスキップ
                    if (!p.colorString().equals(modelPiece.colorString())){
                        continue;
                    }

                    // 3回ロテートしながら検索
                    Piece rotatedPiece = p;
                    for (int r = 1; r < 4; r++) {
                        rotatedPiece = rotatedPiece.rotate();
                        log("Piece r"+ r +": \n" + rotatedPiece.toString());
                        if (rotatedPiece.isSame(modelPiece)) {
                            return new FindResult(m, i, j, r);
                        }
                    }
                }
            }
            return new FindResult();
        }
    }

    static class FindResult {
        final boolean isFound;
        int x, y, rotate;

        FindResult() {
            isFound = false;
        }

        FindResult(int m, int _x, int _y, int _rotate) {
            isFound = true;
            x = _x * m + 1; // 出力時1オリジン
            y = _y * m + 1;
            rotate = _rotate;
        }

        @Override
        public String toString() {
            if (!isFound) return "-1";
            return x + " " + y + " " + rotate;
        }
    }

    static class ModelParser {
        char[][] data;

        ModelParser(int n, List<String> inputData) {
            data = new char[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    data[i][j] = inputData.get(i).charAt(j);
                }
            }
        }

        // ピースのindex番号とmからPiece作成のための文字の場所を示すRangeを返す
        Piece make(int x, int y, int m) {
//            log("make x:"+x+", y:" +y);
            Color[][] pattern = new Color[m][m];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
//                    log("make i:"+i+", j:" +j);
//                    log("data x:"+ (x*m+i)+", y:" +(y*m+j));
                    pattern[i][j] = Color.valueOf(data[x * m + i][y * m + j]);
                }
            }
            return new Piece(pattern);
        }
    }


    static class Piece {
        Piece(Color[][] _pattern) {
            pattern = _pattern;
            m = pattern.length;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    sb.append(pattern[i][j].toString());
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        boolean isSame(Piece p) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    if (p.pattern[i][j] != pattern[i][j]) {
                        return false;
                    }
                }
            }
            return true;
        }

        // BlueとYellowの数を文字列で返す（回転すれば同じになるか検査用)
        private String colorString(){
            int blue=0, yellow=0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    if(pattern[i][j]==Color.B){
                        blue++;
                    }else{
                        yellow++;
                    }
                }
            }
            return blue +"."+ yellow;
        }

        // 90度回転
        Piece rotate() {
            Color[][] result = new Color[m][m];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < m; j++) {
                    result[i][j] = pattern[m - 1 - j][i];
                    //result[i][j] = pattern[j][m - 1 - i];
                }
            }
            return new Piece(result);
        }


        private final int m;
        private final Color[][] pattern;
    }

    enum Color {
        Y, B;

        static Color valueOf(char c) {
            return valueOf(Character.toString(c));
        }
    }

    static boolean isDebug = false; //true;
    private static void log(String s) {
        if (isDebug) {
            System.out.println("DEBUG: " + s);
        }
    }

    private static void out(String s) {
        System.out.println(s);
    }

    private static String str(int i) {
        return Integer.toString(i);
    }
}
