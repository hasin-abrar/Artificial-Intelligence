/**
 * @author ANTU on 21-Jun-18.
 * @project TSP
 */
public class GraphAlgo {
    Path globalPath = null;
    boolean[] visited = null;

    public GraphAlgo(int cityName, int cityCount) {
        globalPath = new Path(cityName);
        visited = new boolean[cityCount];
    }
    void DFS(Node current){
        visited[current.cityName] = true;
        globalPath.travelPath.add(current.cityName);
        if (current.next != null && !visited[current.next.cityName]){
            DFS(current.next);
        }
        if (current.prev != null &&!visited[current.prev.cityName]){
            DFS(current.prev);
        }
    }

    double getTotalPathCost(){
        for (int i = 0; i < TSP.n; i++) { //as last edge was added, linked list has total N values
            int j = globalPath.travelPath.get(i);
            int k = globalPath.travelPath.get(i + 1);
            globalPath.totalCost+= TSP.costMatrix[j][k];
        }
        return globalPath.totalCost;
    }
}
