/**
 * @author ANTU on 30-May-18.
 * @project SolveMaze
 */
public interface Heuristics {

    static int heuristic_Manhattan(Position nextPosition, Position goalPosition) {
        return Math.abs(goalPosition.x - nextPosition.x) + Math.abs(goalPosition.y - nextPosition.y);
    }

    static int heuristic_euclidean(Position nextPosition, Position goalPosition) {
        return (int) Math.sqrt((nextPosition.x - goalPosition.x) * (nextPosition.x - goalPosition.x)
                + (nextPosition.y -goalPosition.y) * (nextPosition.y -goalPosition.y) );
    }

    static int heuristic_max_axis(Position nextPosition, Position goalPosition) {
        return Math.max(Math.abs(goalPosition.x - nextPosition.x),Math.abs(goalPosition.y - nextPosition.y));
    }
}
