import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author ANTU on 20-Jun-18.
 * @project TSP
 */
public class TSP implements Heuristics{
    static int n;
    static List<City> cityList;
    static double[][] costMatrix;

    public static enum USED_METHOD{
        ONE_CITY_SWAPPING,
        TWO_EDGE_SWAPPING
    }

    public static void main(String[] args) throws FileNotFoundException {
        cityList = new ArrayList<>();
        takeInputFromFile();
        costMatrix = new double[n][n];
        calculateCost();
        printCostMatrix();
        System.out.println();

        Path samplePath = new Path(8);
        int[] sampleTour = {8,1,6,3,0,4,5,9,7,2,8};
        for (int i = 0; i < sampleTour.length; i++) {
            samplePath.travelPath.add(sampleTour[i]);
        }
        System.out.println();
        System.out.println(samplePath);

        Path solutionPath = solveWithNearestNeighbor();
//        solutionPath = samplePath;

        solveWithOneCitySwapped(solutionPath);

        solveWithTwoEdgesSwapped(solutionPath);

        solveWithLoopedTwoEdgesSwapped(solutionPath);

        solveWithLoopedOneCitySwapped(solutionPath);

        solveWithMST(solutionPath);

        solveWithCheapestInsertion();

        solveWithNearestInsertion();

    }

    private static Path solveWithNearestNeighbor(){
        System.out.println("Nearest Neighbour:");
        Path solutionPath = Heuristics.nearestNeighbour();
        System.out.print("Solution Path : "+solutionPath);
        System.out.println(" Cost : "+solutionPath.totalCost);
        return solutionPath;
    }

    private static void solveWithOneCitySwapped(Path solutionPath){
        System.out.println();
        System.out.println("One City Swapped:");
        Path oneCitySwappedPath = Heuristics.oneCitySwapping(solutionPath);
        System.out.println(oneCitySwappedPath);
        System.out.println(oneCitySwappedPath.totalCost);
    }

    private static void solveWithTwoEdgesSwapped(Path solutionPath){
        System.out.println();
        System.out.println("Two Edge Swapped:");
        Path twoEdgeSwappingPath = Heuristics.twoEdgeSwapping(solutionPath);
        System.out.println(twoEdgeSwappingPath);
        System.out.println(twoEdgeSwappingPath.totalCost);
    }

    private static void solveWithLoopedTwoEdgesSwapped(Path solutionPath){
        System.out.println();
        System.out.println("Looped Two Edge Swapped:");
        System.out.println("Given Path:");
        System.out.println(solutionPath);
        System.out.println();
        Path loopedTwoEdgeSwappedPath = Heuristics.bestPathLooped(solutionPath,USED_METHOD.TWO_EDGE_SWAPPING);
        System.out.println(loopedTwoEdgeSwappedPath);
        System.out.println(loopedTwoEdgeSwappedPath.totalCost);
    }

    private static void solveWithLoopedOneCitySwapped(Path solutionPath){
        System.out.println();
        System.out.println("Looped One City Swapped:");
        System.out.println("Given Path:");
        System.out.println(solutionPath);
        System.out.println();
        Path loopedOneCitySwappedPath = Heuristics.bestPathLooped(solutionPath,USED_METHOD.ONE_CITY_SWAPPING);
        System.out.println(loopedOneCitySwappedPath);
        System.out.println(loopedOneCitySwappedPath.totalCost);
    }

    private static void solveWithMST(Path solutionPath){
        System.out.println();
        System.out.println("Building MST:");
        System.out.println("Given Path:");
        System.out.println(solutionPath);
        System.out.println();
        Path mstSolutionPath = Heuristics.buildingMST(solutionPath);
        System.out.print("Solution Path : "+mstSolutionPath);
        System.out.println(" Cost : "+mstSolutionPath.totalCost);
    }

    private static void solveWithNearestInsertion(){
        System.out.println();
        System.out.println("Nearest Insertion:");
        Random random = new Random();
        int start = random.nextInt(n);
        Path nearest_insertionPath = Heuristics.nearest_insertion(start);
        System.out.println(nearest_insertionPath);
        System.out.println(nearest_insertionPath.totalCost);
    }

    private static void solveWithCheapestInsertion(){
        System.out.println();
        System.out.println("Cheapest Insertion:");
        Random random = new Random();
        int start = random.nextInt(n);
        Path cheapest_insertion = Heuristics.cheapest_insertion(start);
        System.out.println(cheapest_insertion);
        System.out.println(cheapest_insertion.totalCost);
    }

    private static void printCostMatrix() {
        System.out.println("Formatted distance : ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%.2f",costMatrix[i][j]);
                System.out.print(" ");

            }
            System.out.println();
        }
    }

    private static void calculateCost() {
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if(i == j){
                    costMatrix[i][i] = 0;
                    continue;
                }
                double distance  = euclideanDistance(i,j);
                costMatrix[i][j] = costMatrix[j][i] = distance;
            }
        }
    }

    private static double euclideanDistance(int i, int j) {
        double x1,x2,y1,y2;
        x1 = cityList.get(i).x;
        x2 = cityList.get(j).x;
        y1 = cityList.get(i).y;
        y2 = cityList.get(j).y;
        return Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
    }

    private static void takeInputFromFile() throws FileNotFoundException {
        Scanner input = new Scanner(new File("att48_given.txt"));
        n = Integer.parseInt(input.next());
        for (int i = 0; i < n; i++) {
            double x = Double.parseDouble(input.next());
            double y = Double.parseDouble(input.next());
            City city = new City(x,y,i);
            cityList.add(city);
        }
    }
}
