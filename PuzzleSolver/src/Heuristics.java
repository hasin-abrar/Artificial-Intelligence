import java.util.*;

/**
 * @author ANTU on 29-May-18.
 * @project PuzzleSolver
 */
public interface Heuristics {
    final static int BOARD_SIZE = 3;
    static int heuristic_Manhattan(Board currentBoard, Board goalBoard) {
        int heuVal = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int currentVal = currentBoard.getPositionVal(i,j);
                Position goalPosition = goalBoard.valPosition.get(currentVal);
                heuVal+= Math.abs(i - goalPosition.x) + Math.abs(j - goalPosition.y);
            }
        }
        return heuVal;
    }
    static int heuristic_misplaced_tiles(Board currentBoard, Board goalBoard){
        int heuVal = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if(currentBoard.getPositionVal(i,j) != goalBoard.getPositionVal(i,j)){
                    heuVal++;
                }
            }
        }
        return heuVal;
    }
    static int heuristic_euclidean(Board currentBoard, Board goalBoard){
        float heuVal = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int val = currentBoard.getPositionVal(i,j);
                Position goalPosition = goalBoard.valPosition.get(val);
                heuVal+= Math.sqrt((i - goalPosition.x) * (i - goalPosition.x)
                            + (j -goalPosition.y) * (j -goalPosition.y) );
            }
        }
        return (int)heuVal;
    }
    static int heuristic_row_and_column(Board currentBoard, Board goalBoard){
        int heuVal = 0;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                int val = currentBoard.getPositionVal(i,j);
                Position goalPosition = goalBoard.valPosition.get(val);
                if( i != goalPosition.x) heuVal++;
                if(j != goalPosition.y) heuVal++;
            }
        }
        return heuVal;
    }
    static int heuristic_n_max_swap(Board currentBoard, Board goalBoard){
        int _count = 0, heuVal = 0;
        Set<Integer> misplacedEntriesSet = new HashSet<>();
        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            misplacedEntriesSet.add(i);
        }
        Integer replaceWith = 1;
        int [][]currentPosVal = new int[BOARD_SIZE][BOARD_SIZE];
        HashMap<Integer,Position> valPosition = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                currentPosVal[i][j] = currentBoard.posVal[i][j];
                Position position = new Position(i,j);
                valPosition.put(currentPosVal[i][j],position);
            }
        }
        currentBoard = new Board(currentPosVal,valPosition,BOARD_SIZE);

        Random rand = new Random();
        while (true){
//            _count++;
//            currentBoard.printBoard();
            if(currentBoard.equals(goalBoard) || _count == 15 ){
                break;
            }
            heuVal++;
            Position blankPosition = currentBoard.valPosition.get(0);
            if(blankPosition.x == BOARD_SIZE-1 && blankPosition.y == BOARD_SIZE-1){
//            if(blankPosition.x == 0 && blankPosition.y == 0){
                int takePosition = rand.nextInt(misplacedEntriesSet.size());
                int index = 0;
                Iterator iterator = misplacedEntriesSet.iterator();
                while (iterator.hasNext()){
                    replaceWith = (Integer) iterator.next();
                    if(index == takePosition) break;
                    index++;
                }
                Position newPosition = currentBoard.valPosition.get(replaceWith);
                currentBoard.posVal[newPosition.x][newPosition.y] = 0;
                currentBoard.valPosition.put(0,newPosition);
//                currentBoard.posVal[0][0] = replaceWith;
                currentBoard.posVal[BOARD_SIZE-1][BOARD_SIZE-1] = replaceWith;
//                currentBoard.valPosition.put(replaceWith,new Position(0,0));
                currentBoard.valPosition.put(replaceWith,new Position(BOARD_SIZE-1,BOARD_SIZE-1));
                continue;
            }
            int value = goalBoard.getPositionVal(blankPosition.x,blankPosition.y);
            Position newPosition = currentBoard.valPosition.get(value);

            currentBoard.posVal[newPosition.x][newPosition.y] = 0;
            currentBoard.valPosition.put(0,newPosition);
            currentBoard.posVal[blankPosition.x][blankPosition.y] = value;
            currentBoard.valPosition.put(value,blankPosition);
//            System.out.println(heuVal);
        }
//        if(!currentBoard.equals(goalBoard)) System.out.println("MILE NAI !!");
        return heuVal;
    }

    static double func(double b, int d, int N){
        return Math.pow(b,d+1) - ((N+1)*b) +N;
    }

    static double branching_factor(int d, int N){
        int i = 0, iMax = 2000;
        double xr = 0,xl =0,xu = 4,ea = 10,test,fl,fu;
        fl = func(xl,d,N);
        fu = func (xu, d, N);
        if(fl == 0){
            return xl;
        }
        if(fu == 0){
            return xu;
        }
        while (fl * fu > 0){
            xl += 0.1;
            fl = func(xl,d,N);
            if(xl == xu){
                return 0;
            }
        }
        while (true){
            xr = (xl + xu) / 2;
            i++;
            test = func(xl,d,N) * func (xr, d, N);
            if(test < 0){
                xu = xr;
            }else if(test > 0){
                xl = xr;
            }else{
                break;
            }
            if(i>= iMax){
                break;
            }
        }
        return xr;
    }

}
