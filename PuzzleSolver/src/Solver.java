import sun.net.www.http.Hurryable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author ANTU on 28-May-18.
 * @project PuzzleSolver
 */
public class Solver {
    static PriorityQueue<Board> frontierPQ;
    static Set<Board> generatedBoardSet;
    static HashMap<Board, Integer> boardCost;
    static HashMap<Board,Board> solvedMap;
    static Board goalBoard;
    static int generatedNodes;
    static HEURISTIC_NAME CURRENT_HEURISTIC;

    static void doAStarSearch(){
        int[] fx = {-1, 0, 1, 0};
        int[] fy = {0, -1, 0, 1};
        while (!frontierPQ.isEmpty()){
            Board currentBoard = frontierPQ.poll();
//            System.out.println("Current Board : "+currentBoard.getBoardPriority());
//            currentBoard.printBoard();

            if(currentBoard.equals(goalBoard)){
//                System.out.println("FOUND!!");
                break; // FOUND
            }
            generatedNodes++;
            int blankX = currentBoard.getBlankX();
            int blankY = currentBoard.getBlankY();

            for (int i = 0; i < 4; i++) {
                int newX = blankX + fx[i];
                int newY = blankY + fy[i];
                if(currentBoard.isWithinLimit(newX,newY)){
                    int newCost = boardCost.get(currentBoard) + 1;
                    int currentVal = currentBoard.getPositionVal(newX,newY);
                    HashMap<Integer, Position> nextValuePosition = new HashMap<>();
                    int[][] posVal = new int[Heuristics.BOARD_SIZE][Heuristics.BOARD_SIZE];

                    for (int j = 0; j < Heuristics.BOARD_SIZE; j++) {
                        for (int k = 0; k < Heuristics.BOARD_SIZE; k++) {
                            Position position = new Position(j,k);
                            if(j == newX && k == newY) {
                                posVal[j][k] = 0;
                                nextValuePosition.put(0,position);
                            }else if (j == blankX && k == blankY) {
                                posVal[j][k] = currentVal;
                                nextValuePosition.put(currentVal,position);
                            }else {
                                posVal[j][k] = currentBoard.getPositionVal(j,k);
                                nextValuePosition.put(currentBoard.getPositionVal(j,k),position);
                            }
                        }
                    }

                    Board nextBoard = new Board(posVal,nextValuePosition,Heuristics.BOARD_SIZE);
                    int priorityValue = newCost + heuristicValue(nextBoard,goalBoard);
                    if(!generatedBoardSet.contains(nextBoard) || newCost < boardCost.get(nextBoard)){
                        boardCost.put(nextBoard,newCost);
                        generatedBoardSet.add(nextBoard);

                        nextBoard.setBoardPriority(priorityValue);
                        frontierPQ.add(nextBoard);
                        solvedMap.put(nextBoard,currentBoard);
//                        Uncomment to debug
                        /*
                        System.out.println("next board : "+nextBoard.getBoardPriority());
                        nextBoard.printBoard();
                        System.out.println("Current Board");
                        currentBoard.printBoard();
                        Board outBoard = solvedMap.get(nextBoard);
                        System.out.println("Out of hashed Map");
                        outBoard.printBoard();
                        */
                    }
                }
            }
        }
    }
    public enum HEURISTIC_NAME {
        MISPLACED_TILES,
        MANHATTAN,
        EUCLIDEAN,
        ROW_AND_COL,
        N_MAX_SWAP
    }
    private static int heuristicValue(Board currentBoard, Board goalBoard){
        int heuVal = 0;


        switch (CURRENT_HEURISTIC){
            case MANHATTAN:
                heuVal = Heuristics.heuristic_Manhattan(currentBoard,goalBoard);
                break;
            case EUCLIDEAN:
                heuVal = Heuristics.heuristic_euclidean(currentBoard,goalBoard);
                break;
            case ROW_AND_COL:
                heuVal = Heuristics.heuristic_row_and_column(currentBoard,goalBoard);
                break;
            case MISPLACED_TILES:
                heuVal = Heuristics.heuristic_misplaced_tiles(currentBoard,goalBoard);
                break;
            case N_MAX_SWAP:
                heuVal = Heuristics.heuristic_n_max_swap(currentBoard,goalBoard);
                break;
        }
        return heuVal;
    }

    /*private static int heuristic_Manhattan(Board currentBoard, Board goalBoard) {
        int heuVal = 0;
        for (int i = 0; i < Heuristics.BOARD_SIZE; i++) {
            for (int j = 0; j < Heuristics.BOARD_SIZE; j++) {
                int currentVal = currentBoard.getPositionVal(i,j);
                Position goalPosition = goalBoard.valPosition.get(currentVal);
                heuVal+= Math.abs(i - goalPosition.x) + Math.abs(j - goalPosition.y);
            }
        }
        return heuVal;
    }*/

    private static int showSolvedPuzzle(Board initialBoard){
        if(goalBoard.equals(initialBoard)) return 0;
        Stack<Board> stack = new Stack<>();
        Board board = solvedMap.get(goalBoard);
        int _count = 0;
        if(initialBoard == null){
            System.out.println("initial null");
        }
        if(board == null) {
            System.out.println("NULL");
            goalBoard.printBoard();
            return 0;
        }
//        goalBoard.printBoard();
        stack.push(goalBoard);
//        System.out.println();
        while (true){
//            board.printBoard();
            stack.push(board);
            _count++;
            if(board.equals(initialBoard)){
                break;
            }
            board = solvedMap.get(board);
//            System.out.println();
        }
        while (!stack.isEmpty()){
            Board currentBoard = stack.pop();
            currentBoard.printBoard(); //printing solve
            System.out.println();
        }
        System.out.println("Steps : "+_count);
        return _count;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int _count = 0;
        for(HEURISTIC_NAME heuristic_name: HEURISTIC_NAME.values()) {
            _count++;
            int[][] goalVal = new int[Heuristics.BOARD_SIZE][Heuristics.BOARD_SIZE];
            CURRENT_HEURISTIC = heuristic_name;
            int[][] initialBoardVal = new int[Heuristics.BOARD_SIZE][Heuristics.BOARD_SIZE];
            HashMap<Integer, Position> goalValuePosition = new HashMap<>();
            HashMap<Integer, Position> initialValuePosition = new HashMap<>();
//            int val = 0;
            int val = 1;
            for (int i = 0; i < Heuristics.BOARD_SIZE; i++) {
                for (int j = 0; j < Heuristics.BOARD_SIZE; j++) {
                    goalVal[i][j] = val;
                    Position valuePos = new Position(i, j);
                    goalValuePosition.put(val++, valuePos);
                }
            }
            goalVal[Heuristics.BOARD_SIZE - 1][Heuristics.BOARD_SIZE - 1] = 0;
            Position blankPos = new Position(Heuristics.BOARD_SIZE - 1, Heuristics.BOARD_SIZE - 1);
            goalValuePosition.put(0, blankPos);

            Scanner fileScanner = new Scanner(new File("D:\\Google Drive\\Programming\\Offlines\\PuzzleSolver\\in_last.txt"));

            for (int i = 0; i < Heuristics.BOARD_SIZE; i++) {
                for (int j = 0; j < Heuristics.BOARD_SIZE; j++) {
                    val = Integer.parseInt(fileScanner.nextLine());
                    initialBoardVal[i][j] = val;
                    Position valuePos = new Position(i, j);
                    initialValuePosition.put(val, valuePos);
                }
            }

            Board startBoard = new Board(initialBoardVal, initialValuePosition, Heuristics.BOARD_SIZE);
            goalBoard = new Board(goalVal, goalValuePosition, Heuristics.BOARD_SIZE);
            frontierPQ = new PriorityQueue<>(1, new Comparator<Board>() {
                @Override
                public int compare(Board b1, Board b2) {
                    return b1.getBoardPriority() - b2.getBoardPriority(); //non decreasing order
                }
            });
            generatedBoardSet = new HashSet<>();
            boardCost = new HashMap<>();
            boardCost.put(startBoard, 0);
            solvedMap = new HashMap<>();

            frontierPQ.add(startBoard);
            generatedBoardSet.add(startBoard);
//            startBoard.printBoard();
//            goalBoard.printBoard();

//            break;
            doAStarSearch();
            int steps = showSolvedPuzzle(startBoard);
//            double branchingFactor = findBranchingFactorValue(steps, generatedBoardSet.size());
            System.out.println("Current Heuristic : " + CURRENT_HEURISTIC.toString());
            System.out.println("Number of generated nodes : " + generatedBoardSet.size());
            System.out.println("Branching Factor : " + findBranchingFactorValue(steps, generatedBoardSet.size()));
            System.out.println();
            /*frontierPQ.clear();
            generatedBoardSet.clear();
            boardCost.clear();
            solvedMap.clear();*/
//            if(_count == 2)break;
//            break;
        }
    }

    private static double findBranchingFactorValue(int d, int N) {
        return Heuristics.branching_factor(d,N);
    }
}
