import java.lang.reflect.Array;
import java.util.HashMap;

/**
 * @author ANTU on 28-May-18.
 * @project PuzzleSolver
 */
public class Board {
    int[][] posVal, goalVal;
    private int boardSize, blankX, blankY, boardPriority, goalX, goalY;
    HashMap<Integer, Position> valPosition;

    public Board(int[][] posVal, HashMap<Integer, Position> valPosition, int boardSize) {
        this.posVal = posVal;
        this.valPosition = valPosition;
        this.boardSize = boardSize;
        setBlankXY();
    }

    public boolean isWithinLimit(int x, int y){
        return x>=0 && x<boardSize && y>=0 && y<boardSize;
    }

    public int getPositionVal(int x, int y){
        return posVal[x][y];
    }

    private void setGoalXY(int val , Board goalBoard) {
        boolean foundFlag = false;
        for(int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++){
                if(goalBoard.getPositionVal(i,j) == val){
                    goalX = i;
                    goalY = j;
                    foundFlag = true;
                    break;
                }
            }
            if(foundFlag) break;
        }
    }

    private void setBlankXY() {
        boolean foundFlag = false;
        for(int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++){
                if(posVal[i][j] == 0){
                    blankX = i;
                    blankY = j;
                    foundFlag = true;
                    break;
                }
            }
            if(foundFlag) break;
        }
    }

    public void printBoard(){
        for(int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++) {
                System.out.print(posVal[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void setBoardPriority(int boardPriority) {
        this.boardPriority = boardPriority;
    }

    public int getBoardPriority() {
        return boardPriority;
    }

    public int getBlankX() {
        return blankX;
    }

    public int getBlankY() {
        return blankY;
    }

    @Override
    public boolean equals(Object o) {
        Board board = (Board) o;
        boolean isEqual = true, foundFlag = false;
        for(int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++) {
                if(posVal[i][j] != board.getPositionVal(i,j)){
                    isEqual = false;
                    foundFlag = true;
                    break;
                }
            }
            if(foundFlag) break;
        }
        return isEqual;
    }

    @Override
    public int hashCode() {
        Integer hashValue = 0;
        for(int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++) {
                   hashValue+= (i+1)*posVal[i][j] + (j+2)*posVal[i][j];
            }
        }
        return hashValue.hashCode();
    }

    @Override
    public String toString() {
        String boardValue = "";
        for(int i=0; i<boardSize; i++){
            for (int j=0; j<boardSize; j++) {
                boardValue+= posVal[i][j]+" ";
            }
            boardValue+="\n";
        }
        return boardValue;
    }
}
