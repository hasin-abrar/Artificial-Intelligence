import java.util.HashMap;
import java.util.zip.DeflaterOutputStream;

/**
 * @author ANTU on 20-Jun-18.
 * @project TSP
 */
public interface Heuristics {

    static Path nearestNeighbour(){
        Path minPath = null;
        int cityCount = TSP.n;
        double[][] costMatrix = TSP.costMatrix;
        double minTravelCost = Double.MAX_VALUE;
        for (int start = 0; start < cityCount; start++) {
            Path path = new Path(start);
            boolean[] visited = new boolean[cityCount];
            int _count = 1;
            path.travelPath.add(start);
            int i = start,next = -1; // can be a source of error
            double _min = Double.MAX_VALUE, cost = 0;
            visited[i] = true;
            while (true){
                for (int j = 0; j < cityCount; j++) {
                    if(j == i) continue;
                    if(!visited[j] && costMatrix[i][j] < _min){
                        _min = costMatrix[i][j];
                        next = j;
                    }
                }
                visited[next] = true;
                _count++;
                cost+= _min;
                path.travelPath.add(next);
                if(_count == cityCount){
                    path.travelPath.add(start);
                    cost+= costMatrix[next][start];
                    break;
                }
                i = next;
                _min = Double.MAX_VALUE;
            }
            path.totalCost = cost;

            System.out.print("Inside : "+path);
            System.out.println(" Cost : "+cost);

            if(cost < minTravelCost){
                minTravelCost = cost;
                minPath = path;
            }
        }
        return minPath;
    }

    static void createConnection(Node city1, Node city2){
        city1.next = city2;
        city2.prev = city1;
    }

    static Path nearest_insertion(int start){
        int cityCount = TSP.n;
        double[][] costMatrix = TSP.costMatrix;
        double minTravelCost = Double.MAX_VALUE;
        boolean[] inserted = new boolean[cityCount];
        HashMap<Integer, Node> graph = new HashMap<>();
        Node currentNode = new Node(start);
        graph.put(start, currentNode);
        inserted[start] = true;
        int next = -1; //can be a source of error
        for (int i = 0; i < cityCount; i++) {
            if(i!= start && costMatrix[start][i] < minTravelCost){
                minTravelCost = costMatrix[start][i];
                next = i;
            }
        }
        Node nextNode = new Node(next);
        graph.put(next,nextNode);
        createConnection(currentNode,nextNode);
        createConnection(nextNode,currentNode);
        inserted[next] = true;
        for (int i = 0; i < cityCount - 2; i++) {
            minTravelCost = Double.MAX_VALUE;
            int currentConsidered = -1, nextConsidered = -1;
            for (int j = 0; j < cityCount; j++) {
                if(!inserted[j]) continue;
                for (int k = 0; k < cityCount; k++) {
                    if(!inserted[k]){
                        if(costMatrix[j][k] < minTravelCost){
                            minTravelCost = costMatrix[j][k];
                            currentConsidered = j;
                            nextConsidered = k;
                        }
                    }
                }
            }
            currentNode = graph.get(currentConsidered);
            int currentCity = currentNode.cityName;
            int nextCityName = currentNode.next.cityName;
            int prevCityName = currentNode.prev.cityName;
            double cost1 = costMatrix[prevCityName][nextConsidered] + costMatrix[nextConsidered][currentCity]
                    - costMatrix[prevCityName][currentCity];
            double cost2 = costMatrix[currentCity][nextConsidered] + costMatrix[nextConsidered][nextCityName]
                    - costMatrix[currentCity][nextCityName];
            Node temp = new Node(nextConsidered);
            if(cost1 < cost2){
                Node prevCity = currentNode.prev;
                createConnection(prevCity,temp);
                createConnection(temp,currentNode);
            }else{
                Node nextCity = currentNode.next;
                createConnection(currentNode,temp);
                createConnection(temp,nextCity);
            }
            graph.put(nextConsidered,temp);
            inserted[nextConsidered] = true;
        }
        Path path = new Path(start);
        path.travelPath.add(start);
        currentNode = graph.get(start);
        double cost = 0;
        while (true){
            nextNode = currentNode.next;
            path.travelPath.add(nextNode.cityName);
            cost+= costMatrix[currentNode.cityName][nextNode.cityName];
            if(nextNode.cityName == start) break;
            currentNode = nextNode;
        }
        path.totalCost = cost;
        return path;
    }

    static Path cheapest_insertion(int start) {
        int cityCount = TSP.n;
        double[][] costMatrix = TSP.costMatrix;
        double minTravelCost = Double.MAX_VALUE;
        boolean[] inserted = new boolean[cityCount];
        HashMap<Integer, Node> graph = new HashMap<>();
        Node currentNode = new Node(start);
        graph.put(start, currentNode);
        inserted[start] = true;
        int next = -1; //can be a source of error
        for (int i = 0; i < cityCount; i++) {
            if(i!= start && costMatrix[start][i] < minTravelCost){
                minTravelCost = costMatrix[start][i];
                next = i;
            }
        }
        Node nextNode = new Node(next);
        graph.put(next,nextNode);
        createConnection(currentNode,nextNode);
        createConnection(nextNode,currentNode);
        inserted[next] = true;
        for (int i = 0; i < cityCount - 2; i++) {
            minTravelCost = Double.MAX_VALUE;
            int currentConsidered = -1, nextConsidered = -1,isCurrent = -1, toBeInserted = -1;
            for (int j = 0; j < cityCount; j++) {
                if (!inserted[j]) continue;
                for (int k = 0; k < cityCount; k++) {
                    if(inserted[k]) continue;
                    currentConsidered = j;
                    nextConsidered = k;
                    currentNode = graph.get(currentConsidered);
                    int currentCity = currentNode.cityName;
                    int nextCityName = currentNode.next.cityName;
                    int prevCityName = currentNode.prev.cityName;
//                    double cost1 = costMatrix[prevCityName][nextConsidered] + costMatrix[nextConsidered][currentCity]
//                            - costMatrix[prevCityName][currentCity];
                    double cost2 = costMatrix[currentCity][nextConsidered] + costMatrix[nextConsidered][nextCityName]
                            - costMatrix[currentCity][nextCityName];
                    /*if(cost1 < minTravelCost){
                        minTravelCost = cost1;
                        toBeInserted = nextConsidered;
                    }*/
                    if(cost2 < minTravelCost){
                        minTravelCost = cost2;
                        isCurrent = currentConsidered;
                        toBeInserted = nextConsidered;
                    }
                }
            }
            Node temp = new Node(toBeInserted);
            Node currentCity = graph.get(isCurrent);
            Node currentCityNext = currentCity.next;
            createConnection(currentCity,temp);
            createConnection(temp,currentCityNext);
            inserted[toBeInserted] = true;
            graph.put(toBeInserted,temp);
        }
        return createPath(graph,start);
    }

    static HashMap<Integer,Node> createGraph(Path givenPath){
        HashMap<Integer, Node> graph = new HashMap<>();
        Node current = new Node(givenPath.startingCity);
        graph.put(givenPath.startingCity,current);
        for (Integer next :
                givenPath.travelPath) {
            Node nextNode = null;
            if(graph.containsKey(next)){
                nextNode = graph.get(next);
            }else{
                nextNode = new Node(next);
            }
            if(nextNode.equals(current)) continue;
            current.next = nextNode;
            nextNode.prev = current;
            current = nextNode;
            graph.put(next,nextNode);
        }
        return graph;
    }

    static Path oneCitySwapping(Path givenPath){
        int cityCount = TSP.n;
        double[][] costMatrix = TSP.costMatrix;
        double minTravelCost = Double.MAX_VALUE;
        HashMap<Integer, Node> graph = createGraph(givenPath);
        double _maxCost = Double.MIN_VALUE;
        int swappedCity = -1, swappedCityPrev = -1;
        for (int city = 0; city < cityCount; city++) {
            Node pickedCityNode = graph.get(city);
            Node prevCityNode = pickedCityNode.prev;
            Node nextCityNode = pickedCityNode.next;
            for (int i = 0; i < cityCount; i++) {
                if(i == pickedCityNode.cityName || i == prevCityNode.cityName) continue;
                Node consideredCityNode = graph.get(i);
                double cost1 = costMatrix[prevCityNode.cityName][pickedCityNode.cityName]
                        + costMatrix[pickedCityNode.cityName][nextCityNode.cityName]
                        + costMatrix[consideredCityNode.cityName][consideredCityNode.next.cityName];
                double cost2 = costMatrix[prevCityNode.cityName][nextCityNode.cityName]
                        + costMatrix[consideredCityNode.cityName][pickedCityNode.cityName]
                        + costMatrix[pickedCityNode.cityName][consideredCityNode.next.cityName];
                double diff = cost1 - cost2;
                /*if(cost2 < cost1){
                    System.out.println(diff +" _ "+ _maxCost);
                    System.out.println((diff > _maxCost));
                    System.out.println(pickedCityNode.cityName+" : "+i+" - "+consideredCityNode.next);
                }*/
                if(cost2 < cost1 && diff > _maxCost){
                    _maxCost = diff;
                    swappedCity = pickedCityNode.cityName;
                    swappedCityPrev = i;
                }
            }
        }
        if(swappedCity != -1){
            Node consideredCityNode = graph.get(swappedCityPrev);
            Node pickedCityNode = graph.get(swappedCity);
            Node prevCityNode = pickedCityNode.prev;
            Node nextCityNode = pickedCityNode.next;
            Node consideredNodeNext = consideredCityNode.next;
            System.out.println(consideredCityNode+"-"+consideredNodeNext);
            System.out.println(pickedCityNode);
            prevCityNode.next = nextCityNode;
            nextCityNode.prev = prevCityNode;
            consideredCityNode.next = pickedCityNode;
            pickedCityNode.prev = consideredCityNode;
            pickedCityNode.next = consideredNodeNext;
            consideredNodeNext.prev = pickedCityNode;
        }
        /*Path path = new Path(givenPath.startingCity);
        path.travelPath.add(givenPath.startingCity);
        Node currentNode = graph.get(givenPath.startingCity);
        double cost = 0;
        while (true){
            Node nextNode = currentNode.next;
            path.travelPath.add(nextNode.cityName);
            cost+= costMatrix[currentNode.cityName][nextNode.cityName];
            if(nextNode.cityName == givenPath.startingCity) break;
            currentNode = nextNode;
        }
        path.totalCost = cost;*/
        return createPath(graph,givenPath.startingCity);
    }

    static Path twoEdgeSwapping(Path givenPath){
        int cityCount = TSP.n;
        double[][] costMatrix = TSP.costMatrix;
        double maxTravelCost = -1;
        Node fPick1 = null,fPick1Next = null,fpick2 = null,fPick2Next = null, fPick2Prev = null, fPick2NextNext = null;
        HashMap<Integer, Node> graph  = createGraph(givenPath);
        for (int city = 0; city < cityCount; city++) {
            Node pick1 = graph.get(city);
            Node pick1Prev = pick1.prev;
            Node pick1Next = pick1.next;
            for (int i = 0; i < cityCount; i++) {
                if(i == pick1.cityName || i == pick1Prev.cityName || i == pick1Next.cityName) continue;
                Node pick2 = graph.get(i);
                Node pick2Prev = pick2.prev;
                Node pick2Next = pick2.next;
                Node pick2NextNext = pick2Next.next;
                double cost1 = costMatrix[pick1.cityName][pick1Next.cityName]
                        + costMatrix[pick2Prev.cityName][pick2.cityName]
                        + costMatrix[pick2Next.cityName][pick2NextNext.cityName];
                double cost2 = costMatrix[pick1.cityName][pick2.cityName]
                        + costMatrix[pick2Next.cityName][pick1Next.cityName]
                        + costMatrix[pick2Prev.cityName][pick2NextNext.cityName];
                double cost3 = costMatrix[pick1.cityName][pick2Next.cityName]
                        + costMatrix[pick2.cityName][pick1Next.cityName]
                        + costMatrix[pick2Prev.cityName][pick2NextNext.cityName];
                double diff1 = cost1 - cost2;
                double diff2 = cost1 - cost3;
                /*if(cost2< cost1 || cost3 < cost1){
                    System.out.println(cost1);
                    System.out.println(cost2);
                    System.out.println(cost3);
                }*/
                if(cost2 < cost1 && diff1 > maxTravelCost){
                    maxTravelCost = diff1;
                    fPick1 = pick1;
                    fPick1Next = pick1Next;
                    fpick2 = pick2;
                    fPick2Next = pick2Next;
                    fPick2Prev = pick2Prev;
                    fPick2NextNext = pick2NextNext;
                }
                if(cost3 < cost1 && diff2 > maxTravelCost){
                    maxTravelCost = diff2;
                    fPick1 = pick1;
                    fPick1Next = pick1Next;
                    fpick2 = pick2Next;
                    fPick2Next = pick2;
                    fPick2Prev = pick2Prev;
                    fPick2NextNext = pick2NextNext;
                }
            }
        }
        if(maxTravelCost != -1){
            System.out.println(fPick1+"-"+fPick1Next);
            System.out.println(fpick2+"-"+fPick2Next);
            createConnection(fPick1,fpick2);
            createConnection(fpick2,fPick2Next);
            createConnection(fPick2Next,fPick1Next);
            createConnection(fPick2Prev,fPick2NextNext);
        }
        return createPath(graph,givenPath.startingCity);
    }

    static Path createPath(HashMap<Integer, Node> graph, int startingCity) {
        double[][] costMatrix = TSP.costMatrix;
        Path path = new Path(startingCity);
        path.travelPath.add(startingCity);
        Node currentNode = graph.get(startingCity);
        double cost = 0;
        while (true){
            Node nextNode = currentNode.next;
            path.travelPath.add(nextNode.cityName);
            cost+= costMatrix[currentNode.cityName][nextNode.cityName];
            if(nextNode.cityName == startingCity) break;
            currentNode = nextNode;
        }
        path.totalCost = cost;
        return path;
    }

    static Path bestPathLooped(Path givenPath, TSP.USED_METHOD used_method){
        double maxCost = Double.MAX_VALUE;
        Path path = givenPath;
        double pathCost = 0;
        while (true){
            switch (used_method){
                case ONE_CITY_SWAPPING:
                    path = oneCitySwapping(path);
                    break;
                case TWO_EDGE_SWAPPING:
                    path = twoEdgeSwapping(path);
                    break;
            }
            pathCost = path.totalCost;
            if(maxCost <= pathCost){
                break;
            }else{
                maxCost = pathCost;
            }
            System.out.println(path);
            System.out.println(maxCost);
        }
        return path;
    }

    static Path buildingMST(Path possiblePath){
        int cityCount = TSP.n;
        double[][] costMatrix = TSP.costMatrix;
        double _max = Double.MIN_VALUE,_min = Double.MAX_VALUE;
        HashMap<Integer, Node> mst = new HashMap<>();
        Path minPath = null;
        //finding the weighted edge cost
        for (int i = 0; i < cityCount-1; i++) {
            int j = possiblePath.travelPath.get(i);
            int k = possiblePath.travelPath.get(i+1);
            if(costMatrix[j][k] > _max){
                _max = costMatrix[j][k];
            }
        }
        //building the MST
        for (int i = 0; i < cityCount; i++) {
            int j = possiblePath.travelPath.get(i);
            int k = possiblePath.travelPath.get(i+1);
            if(costMatrix[j][k] == _max){
                System.out.println(j+"-"+k);
                continue;
            }
            Node node1,node2;
            if(!mst.containsKey(j)){
                node1 = new Node(j);
            }else{
                node1 = mst.get(j);
            }
            if(!mst.containsKey(k)){
                node2 = new Node(k);
            }else{
                node2 = mst.get(k);
            }
            createConnection(node1,node2);
            mst.put(j,node1);
            mst.put(k,node2);
        }

        for (int i = 0; i < cityCount; i++) {
            Node root = mst.get(i);
            GraphAlgo graphAlgo = new GraphAlgo(root.cityName,cityCount);
            graphAlgo.DFS(root);
            graphAlgo.globalPath.travelPath.add(root.cityName); //creating the last path in tsp
            double pathCost = graphAlgo.getTotalPathCost();
            System.out.println("Starting Node: "+i);
            System.out.print("Path: "+graphAlgo.globalPath);
            System.out.println(" Cost : "+pathCost);
            if(pathCost < _min){
                _min = pathCost;
                minPath = graphAlgo.globalPath;
            }
        }
        return minPath;
    }

}
