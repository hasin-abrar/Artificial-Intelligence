/**
 * @author ANTU on 27-Jul-18.
 * @project TicTacToe
 */
public class Position {
    int x,y,value;

    public Position(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    @Override
    public String toString() {
        return x +" "+y+" : "+value;
    }
}
