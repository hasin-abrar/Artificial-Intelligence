import java.util.Random;

/**
 * @author ANTU on 23-Jun-18.
 * @project PuzzleSolver
 */
public class BoardGenerator {
    public static void main(String[] args) {
        int[][] goalVal = new int[Heuristics.BOARD_SIZE][Heuristics.BOARD_SIZE];
        int val = 0;
        for(int i=0; i<Heuristics.BOARD_SIZE; i++){
            for (int j=0; j<Heuristics.BOARD_SIZE; j++){
                goalVal[i][j] = val;
            }
        }
        int steps = 12, blankX = 0,blankY = 0;
        Random random = new Random();
        for (int i = 0; i < steps; i++) {
            int rand = random.nextInt(4);

        }

    }
    public static void swap(int[][]matrix, int i,int j, int p, int q){
        int x = matrix[i][j];
        matrix[i][j] = matrix[p][q];
        matrix[p][q] = x;
    }
}
