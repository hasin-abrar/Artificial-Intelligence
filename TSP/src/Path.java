import java.util.LinkedList;
import java.util.List;

/**
 * @author ANTU on 20-Jun-18.
 * @project TSP
 */
public class Path {
    List<Integer> travelPath;
    int startingCity;
    double totalCost;

    public Path(int startingCity) {
        this.startingCity = startingCity;
        travelPath = new LinkedList<>();
        totalCost = 0;
    }

    @Override
    public String toString() {
        String path = "";
        for (Integer cityName :
                travelPath) {
            path+= cityName+" ";
        }
        return path;
    }
}
