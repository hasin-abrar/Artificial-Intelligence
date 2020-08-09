import java.util.ArrayList;

/**
 * @author ANTU on 27-Jul-18.
 * @project TicTacToe
 */
public class MoveManager {
    int inf = Integer.MAX_VALUE;
    ArrayList<Position> possibleActions;

    public MoveManager(){
        possibleActions = new ArrayList<>();
    }

    Position findBestMove(Board board){
        int alpha = -inf;
        int beta = inf;
        return MaxValue(board,alpha,beta);
    }

    private Position MaxValue(Board board, int alpha, int beta) {
        int utility = board.getUtility();
        if (utility != -1){
//            System.out.println(utility);
//            board.printBoard();
            return new Position(-1,-1,utility);
        }
        int v = -inf;
        possibleActions = board.getPossibleActions();
        Position resultPosition = new Position(-1,-1,-1);
        for (Position position :
                possibleActions) {
            Board currentBoard = new Board(board);
            currentBoard.updateBoard(position,TicTacToe.COMPUTER);
//            currentBoard.printBoard();
//            System.out.println("MaxValue: "+position);
            Position newPosition = MinValue(currentBoard,alpha,beta);
//            System.out.println("MaxValue: "+position+" "+newPosition.value);
            if(v < newPosition.value){
                v = newPosition.value;
                resultPosition.x = position.x;
                resultPosition.y = position.y;
                resultPosition.value = newPosition.value;
//                System.out.println("Max: "+resultPosition);
            }
//            if(v >= beta) return resultPosition;
            alpha = Math.max(alpha,v);
        }
//        System.out.println(alpha+ " "+ beta);
        return resultPosition;
    }

    private Position MinValue(Board board, int alpha, int beta) {
        int utility = board.getUtility();
        if (utility != -1){
//            System.out.println(utility);
//            board.printBoard();
            return new Position(-1,-1,utility);
        }
        int v = inf;
        possibleActions = board.getPossibleActions();
        Position resultPosition = new Position(-1,-1,-1);
        for (Position position :
                possibleActions) {
            Board currentBoard = new Board(board);
            currentBoard.updateBoard(position,TicTacToe.HUMAN);
//            currentBoard.printBoard();
//            System.out.println("MinValue: "+position);
            Position newPosition = MaxValue(currentBoard,alpha,beta);
//            System.out.println("MinValue: "+position+" "+newPosition.value);
            if(v > newPosition.value){
                v = newPosition.value;
                resultPosition.x = position.x;
                resultPosition.y = position.y;
                resultPosition.value = newPosition.value;
//                System.out.println("Min: "+resultPosition);
            }
//            if(v <= alpha) return resultPosition;
            beta = Math.min(beta,v);
        }
        return resultPosition;
    }
}
