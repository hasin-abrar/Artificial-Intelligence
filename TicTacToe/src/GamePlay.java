import java.util.Random;
import java.util.Scanner;

/**
 * @author ANTU on 27-Jul-18.
 * @project TicTacToe
 */
public class GamePlay {
    Board board;
    MoveManager moveManager;

    public GamePlay(Board board, MoveManager moveManager) {
        this.board = board;
        this.moveManager = moveManager;
    }

    public void play(){
        Scanner in = new Scanner(System.in);
        int x = -1,y = -1;
        System.out.println("Press 1 if you want the first turn, 0 otherwise");
        int who = Integer.parseInt(in.next());
        if(who != 1){
            Random random = new Random();
            int fx = random.nextInt(TicTacToe.boardSize);
            int fy = random.nextInt(TicTacToe.boardSize);
            System.out.println("Computer's turn:");
            board.updateBoard(new Position(fx,fy,-1),TicTacToe.COMPUTER);
            board.printBoard();
        }
        while (true){
            System.out.println("Your turn:");
            while (true){
                System.out.println("board x: ");
                x = Integer.parseInt(in.next());
                System.out.println("board y: ");
                y = Integer.parseInt(in.next());
                if(board.isValidInput(x,y)) break;
                else {
                    System.out.println("Wrong input!");
                }
            }
            board.updateBoard(new Position(x,y,-1),TicTacToe.HUMAN);
            board.printBoard();
            if(isGameEnded()) break;
            System.out.println("Computer's turn:");
            Position position = moveManager.findBestMove(board);
            System.out.println(position);
            board.updateBoard(position,TicTacToe.COMPUTER);
            board.printBoard();
            if(isGameEnded()) break;
        }
    }

    boolean isGameEnded(){
        int utility = board.getUtility();
        if(utility == -1) return false;
        else if (utility == 0) {
            //Human have won!
            System.out.println("Congratulation! You have won the game!");
            return true;
        }else if (utility == 1) {
            //Human have won!
            System.out.println("Draw!!");
            return true;
        } else{
            //Match draw
            System.out.println("You have lost the game!");
            return true;
        }
        /*if(player == TicTacToe.HUMAN) {
            int humanUtility = board.getUtility();
            if (humanUtility == 0) {
                //Human have won!
                System.out.println("Congratulation! You have won the game!");
                return true;
            } else if (humanUtility == 2) {
                //Match draw
                System.out.println("Draw!!");
                return true;
            }
            return false;
        }
        int computerUtility = board.getUtility();
        if (computerUtility == 1) {
            //Human have won!
            System.out.println("You have lost the game!");
            return true;
        } else if (computerUtility == 2) {
            //Match draw
            System.out.println("Draw!!");
            return true;
        }
        return false;*/
    }
}
