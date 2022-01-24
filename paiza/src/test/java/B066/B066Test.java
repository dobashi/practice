package B066;
import org.junit.Test;
import static B066.Main.Color;
import static B066.Main.Color.B;
import static B066.Main.Color.Y;
import static B066.Main.Piece;

public class B066Test{
    @Test
    public void testMain(){
        Color[][] pattern = {{B, Y}, {Y, Y}};

        Piece p = new Piece(pattern);
        log(p.toString());
        Piece r = p.rotate();
        log(r.toString());

    }


    private static void log(String s) {
        System.out.println("DEBUG: " + s);
    }


}
