import java.util.HashMap;
import java.util.Random;

/**
 * @author ANTU on 30-May-18.
 * @project SolveMaze
 */
public class Maze {
    String[][] mazeArray;
    private int boardSize, blankX, blankY, boardPriority, goalX, goalY,frequency;
    Position startPosition, goalPosition;
    HashMap<Integer, Position> valPosition;

    public Maze(int frequency, int boardSize) {
        this.frequency = frequency;
        this.boardSize = boardSize;
        this.mazeArray = new String[boardSize][boardSize];
        this.valPosition = new HashMap<>();
    }

    public Maze(Maze maze){
        this.frequency = maze.frequency;
        this.boardSize = maze.boardSize;
        this.valPosition = new HashMap<>();
        this.startPosition = maze.startPosition;
        this.goalPosition = maze.goalPosition;
        this.mazeArray = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.mazeArray[i][j] = maze.mazeArray[i][j];
            }
        }
    }

    public boolean isWithinLimit(int x, int y){
        return x>=0 && x<boardSize && y>=0 && y<boardSize;
    }

    public String getPositionVal(int x, int y){
        return mazeArray[x][y];
    }


    public void createNewMaze(int startX, int startY, int goalX, int goalY) {
        Random random = new Random();
        int val;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                val = random.nextInt(100);
                if(val < frequency){
                    mazeArray[i][j] = "B";
                }else{
                    mazeArray[i][j] = ".";
                }
            }
        }
        startPosition = new Position(startX,startY);
        goalPosition = new Position(goalX,goalY);
        mazeArray[startX][startY] = "S";
        mazeArray[goalX][goalY] = "G";
    }

    public void printMaze(){
        for(int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++) {
                System.out.print(mazeArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidPosition(int nx, int ny) {
        if(nx >= 0 && nx < boardSize && ny >= 0 && ny < boardSize
                && !mazeArray[nx][ny].equals("B")) return true;
        return false;
    }
}
