import graphics.MyGraphic;
import logic.Board;

public class S28232Project04 {
    public static void main(String[] args) {
        MyGraphic g = new MyGraphic();
        Board b = new Board();
        g.init(b);
        b.init(g);
    }
}
