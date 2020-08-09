import java.util.*;

/**
 * @author ANTU on 31-May-18.
 * @project SolveMaze
 */
public class Solver implements Heuristics{
    PriorityQueue<Position> frontier;
    Set<Position> exploredSet;
    HashMap<Position, Integer> costMap;
    HashMap<Position,Position> pathMap;
    Maze maze;
    HEURISTIC_NAME USE_HEURISTIC;
    double expandedNodes, pathLength;

    public Solver(Maze maze, int heuristicIndex) {
        this.maze = maze;
        frontier = new PriorityQueue<>(1, new Comparator<Position>() {
            @Override
            public int compare(Position p1, Position p2) {
                return p1.priority - p2.priority;
            }
        });
        exploredSet = new HashSet<>();
        costMap = new HashMap<>();
        pathMap = new HashMap<>();
        switch (heuristicIndex){
            case 0:
                USE_HEURISTIC = HEURISTIC_NAME.MAX_AXIS;
                break;
            case 1:
                USE_HEURISTIC = HEURISTIC_NAME.MANHATTAN;
                break;
            case 2:
                USE_HEURISTIC = HEURISTIC_NAME.EUCLIDEAN;
                break;
            default:
                System.out.println("INVALID");
                break;
        }
    }

    public boolean isMazeSolvable(){
        int[] fx = {-1, 0, 1, 0};
        int[] fy = {0, -1, 0, 1};
        boolean isSolvable = false;
        Position startPosition = maze.startPosition;
        Position goalPosition = maze.goalPosition;
        startPosition.priority = 0;
        frontier.add(startPosition);
        costMap.put(startPosition,0);
        while (!frontier.isEmpty()){
            Position currentPosition = frontier.poll();
            if(!maze.mazeArray[currentPosition.x][currentPosition.y].equals("S") &&
                    !maze.mazeArray[currentPosition.x][currentPosition.y].equals("G")){
                maze.mazeArray[currentPosition.x][currentPosition.y] = "E";
                expandedNodes++;
            }
            if(currentPosition.equals(goalPosition)){
                isSolvable = true;
                break;
            }
            for (int k = 0; k < 4; k++) {
                int nx = currentPosition.x + fx[k];
                int ny = currentPosition.y + fy[k];
                if(maze.isValidPosition(nx,ny)){
                    int nextCost = costMap.get(currentPosition) + 1;
                    Position nextPosition = new Position(nx,ny);
                    if(!exploredSet.contains(nextPosition) || nextCost < costMap.get(nextPosition)){
                        costMap.put(nextPosition,nextCost);
                        exploredSet.add(nextPosition);
                        nextPosition.priority = nextCost + heuristicCost(nextPosition);
                        frontier.add(nextPosition);
                        pathMap.put(nextPosition,currentPosition);
                    }
                }
            }
        }
        return isSolvable;
    }

    public enum HEURISTIC_NAME {
        MANHATTAN,
        MAX_AXIS,
        EUCLIDEAN
    }

    private int heuristicCost(Position nextPosition) {
        int heuVal = 0;
//        USE_HEURISTIC = HEURISTIC_NAME.MAX_AXIS;
        switch (USE_HEURISTIC){
            case MANHATTAN:
                heuVal = Heuristics.heuristic_Manhattan(nextPosition,maze.goalPosition);
                break;
            case EUCLIDEAN:
                heuVal = Heuristics.heuristic_euclidean(nextPosition,maze.goalPosition);
                break;
            case MAX_AXIS:
                heuVal = Heuristics.heuristic_max_axis(nextPosition,maze.goalPosition);
                break;
        }
        return heuVal;
    }

    public void showPath(){
        Position startPosition = maze.startPosition;
        Position goalPosition = maze.goalPosition;
        if(goalPosition.equals(startPosition)) return;

        Position currentPostion = pathMap.get(goalPosition);
        int _count = 0;
        while (true){
            _count++;
//            System.out.println(currentPostion);
            if(startPosition.equals(currentPostion)) {
                pathLength++;
                break;
            }
            maze.mazeArray[currentPostion.x][currentPostion.y] = "P";
            pathLength++;
            currentPostion = pathMap.get(currentPostion);
        }
        maze.printMaze();
    }
}
