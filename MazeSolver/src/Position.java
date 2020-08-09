/**
 * @author ANTU on 28-May-18.
 * @project SolveMaze
 */
public class Position {
    public int x,y,priority;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.priority = 0;
    }

    @Override
    public boolean equals(Object o) {
        Position position = (Position) o;
        return (x == position.x) && (y == position.y);
    }

    @Override
    public int hashCode() {
        Integer hashValue = x*3 + y*4;
        return hashValue.hashCode();
    }

    @Override
    public String toString() {
        return "x = "+x+" y = "+y;
    }
}
