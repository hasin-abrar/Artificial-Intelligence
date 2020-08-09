/**
 * @author ANTU on 27-Jul-18.
 * @project TicTacToe
 */
public class TicTacToe {
    static int boardSize = 4;
    static int HUMAN = 1; // move is X
    static int COMPUTER = 2; // move is O

    public static void main(String[] args) {
        Board board = new Board();
        MoveManager moveManager = new MoveManager();
        GamePlay gamePlay = new GamePlay(board,moveManager);
        board.printBoard();
        gamePlay.play();
    }
}
