import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author ANTU on 30-May-18.
 * @project SolveMaze
 */
public class SolveMaze {
    private static int MAZE_SIZE, FREQUENCY, startX, startY, goalX, goalY;

    public static void main(String[] args) throws FileNotFoundException {
//        takeInput();
        takeInputFromFile();
        Maze mainMaze = new Maze(FREQUENCY, MAZE_SIZE);
        mainMaze.createNewMaze(startX, startY, goalX, goalY);
        mainMaze.printMaze();
        System.out.println();
        for (int i = 0; i < 3; i++) {
            Maze maze = new Maze(mainMaze);
            Solver solver = new Solver(maze,i);
            if (solver.isMazeSolvable()) {
                solver.showPath();
                if (solver.pathLength != 0) {
                    System.out.println();
                    double ratio = solver.expandedNodes / solver.pathLength;
                    System.out.println("Frequency : " + FREQUENCY);
                    System.out.println("Heuristic Name : " + solver.USE_HEURISTIC.toString());
                    System.out.println("Expanded nodes : " + solver.expandedNodes);
                    System.out.println("Path length : " + solver.pathLength);
                    System.out.println("Ratio of expanded nodes and path length : " + ratio);
                }
            } else {
                System.out.println("There is no path available");
            }
        }
    }

    public static void takeInput(){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter maze size");
        MAZE_SIZE = Integer.parseInt(input.next());
        System.out.println("Enter frequency");
        FREQUENCY = Integer.parseInt(input.next());
        System.out.println("Insert startX");
        startX = Integer.parseInt(input.next());
        System.out.println("Insert startY");
        startY = Integer.parseInt(input.next());
        System.out.println("Insert goalX");
        goalX = Integer.parseInt(input.next());
        System.out.println("Insert goalY");
        goalY = Integer.parseInt(input.next());
    }
    private static void takeInputFromFile() throws FileNotFoundException {
        Scanner input = new Scanner(new File("input.txt"));
        while (input.hasNext()){
            MAZE_SIZE = Integer.parseInt(input.next());
            FREQUENCY = Integer.parseInt(input.next());
            startX = Integer.parseInt(input.next());
            startY = Integer.parseInt(input.next());
            goalX = Integer.parseInt(input.next());
            goalY = Integer.parseInt(input.next());
        }
    }
}
