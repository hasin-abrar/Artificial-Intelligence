import java.util.ArrayList;
import java.util.List;

/**
 * @author ANTU on 21-Jun-18.
 * @project TSP
 */
public class Node {
    int cityName;
//    List<Node> edges;
    Node next, prev;

    public Node(int cityName) {
        this.cityName = cityName;
//        edges = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        return this.cityName == ((Node)o).cityName;
    }

    @Override
    public String toString() {
        return ""+cityName;
    }
}
