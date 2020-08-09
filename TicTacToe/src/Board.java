import java.util.ArrayList;

/**
 * @author ANTU on 27-Jul-18.
 * @project TicTacToe
 */
public class Board {
    String[][] boardArray;
    int boardSize;

    public Board() {
        boardSize = TicTacToe.boardSize;
        boardArray = new String[boardSize][boardSize];
        /*String[][] test = {
                {"O",".","."},
                {".","X","."},
                {".",".","."}
        };
        boardArray = test;*/
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boardArray[i][j] = ".";
            }
        }
    }
    public Board(Board board){
        boardSize = TicTacToe.boardSize;
        boardArray = new String[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                this.boardArray[i][j] = board.boardArray[i][j];
            }
        }
    }

    void printBoard(){
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(boardArray[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isValidInput(int x, int y){
        return (x >= 0 && x < boardSize && y >= 0 && y < boardSize) && boardArray[x][y].equals(".");
    }

    /*
    * Win = 2, Draw = 1, Lose = 0, Game On = -1
    * */
    public int getUtility() { // With respect to max that is Computer
        boolean isHumanWin = false, isComputerWin = false, isStillPlayable = false;
        int humanCountRow = 0, computerCountRow = 0, humanCountCol = 0, computerCountCol = 0,
                humanCountDiagonal1 = 0, computerCountDiagonal1 = 0,
                humanCountDiagonal2 = 0, computerCountDiagonal2 = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(boardArray[i][j].equals("X")) humanCountRow++;
                if(boardArray[i][j].equals("O")) computerCountRow++;
                if(boardArray[j][i].equals("X")) humanCountCol++;
                if(boardArray[j][i].equals("O")) computerCountCol++;
                if(boardArray[i][j].equals(".")) isStillPlayable = true; //check if the game should be called draw
            }
            if(humanCountRow == boardSize || humanCountCol == boardSize) {
                isHumanWin = true;
                break;
            }
            if(computerCountRow == boardSize || computerCountCol == boardSize){
                isComputerWin = true;
                break;
            }
            humanCountRow = computerCountRow = humanCountCol = computerCountCol = 0;
        }
        if(isHumanWin) return 0;
        if(isComputerWin) return 2;
        for (int i = 0; i < boardSize; i++) {
            if(boardArray[i][i].equals("X")) humanCountDiagonal1++;
            if(boardArray[i][i].equals("O")) computerCountDiagonal1++;
            if(boardArray[i][boardSize-1-i].equals("X")) humanCountDiagonal2++;
            if(boardArray[i][boardSize-1-i].equals("O")) computerCountDiagonal2++;
        }
        if(humanCountDiagonal1 == boardSize || humanCountDiagonal2 == boardSize){
//            isHumanWin = true;
            return 0;
        }
        if(computerCountDiagonal1 == boardSize || computerCountDiagonal2 == boardSize){
//            isComputerWin = true;
            return 2;
        }
        if(isStillPlayable) return -1;
        else return 1;
    }

    public ArrayList<Position> getPossibleActions() {
        ArrayList<Position> possibleActionsList = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if(boardArray[i][j].equals(".")){
                    Position position = new Position(i,j,-1);
                    possibleActionsList.add(position);
                }
            }
        }
        return possibleActionsList;
    }

    public void updateBoard(Position position,int player) {
        if(player == TicTacToe.HUMAN){
            boardArray[position.x][position.y] = "X";
        }else{
            boardArray[position.x][position.y] = "O";
        }
    }
}
