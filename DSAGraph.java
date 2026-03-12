public class DSAGraph {
    DSALinkedList vertices; 
    int edgeCount;

    //private class to store nodes and levels for BFS
    private class BFSNode {
        public DSAGraphNode vertex;
        public int level;

        public BFSNode(DSAGraphNode v, int l) {
            this.vertex = v;
            this.level = l;
        }

        public DSAGraphNode getNode() {
            return vertex;
        }

        public int getLevel() {
            return level;
        }
    }

    public DSAGraph() {
        vertices = new DSALinkedList();
        edgeCount = 0;
    }

    //add a vertex to the graph
    public void addVertex(Object label, Object value) throws Exception {
        DSAGraphNode newNode = new DSAGraphNode(label, value);
        vertices.insertLast(newNode); 
    }

    //remove a vertex from the graph 
    public void removeVertex(Object label) throws Exception {
        DSAGraphNode nodeToRemove = findVertex(label);

        if (nodeToRemove == null) {
            throw new Exception("Vertex not found!");
        }

        //remove all edges to this vertex 
        for (int i = 0; i < vertices.getCount(); i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            DSAGraphNode[] adjacent = v.getAdjacentVertex();

            for (int j = 0; j < adjacent.length; j++) {
                if (adjacent[j].getLabel().equals(label)) {
                    v.removeEdge(adjacent[j]);
                    edgeCount--;
                }
            }
        }

        boolean found = false;

        //remove the vertex from the list 
        for (int i = 0; i < vertices.getCount() && !found; i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            if (v.getLabel().equals(label)) {
                vertices.removeAt(i);
                found = true;
            }
        }
    }

    //add a weighted edge
    public void addEdge(Object fromLabel, Object toLabel, double weight) throws Exception  {
        DSAGraphNode fromVertex = findVertex(fromLabel);
        DSAGraphNode toVertex = findVertex(toLabel);

        if (fromVertex == null || toVertex == null) {
            throw new Exception("The vertex/vertices were not found!");
        }

        fromVertex.addEdge(toVertex, weight); //added edge to the vertex
        toVertex.addEdge(fromVertex, weight);//add edge in the opposite direction to create an undirected graph
        edgeCount++;
    }

    //remove an edge
    public void removeEdge(Object fromLabel, Object toLabel) throws Exception {
        DSAGraphNode fromVertex = findVertex(fromLabel);
        DSAGraphNode toVertex = findVertex(toLabel);

        if (fromVertex == null || toVertex == null) {
            throw new Exception("The vertex/vertices were not found!");
        }

        fromVertex.removeEdge(toVertex); //removed edge to the vertex
        toVertex.removeEdge(fromVertex);//remove edge in the opposite direction as graph is undirected
        edgeCount--;
    }

    //find a vertex by its label
    public boolean hasVertex(Object label) throws Exception {
        return (findVertex(label) != null);
    }

    //get the count of vertices
    public int getVertexCount() {
        return vertices.getCount();
    }

    //get the count of edges
    public int getEdgeCount() {
        return edgeCount;
    }

    //get the vertex with a spevifiv label
    public DSAGraphNode getVertex(Object label) throws Exception {
        return findVertex(label);
    }

    //get the weight of an edge
    public double getEdgeWeight(Object fromLabel, Object toLabel) throws Exception {
        DSAGraphNode fromVertex = findVertex(fromLabel);
        DSAGraphNode toVertex = findVertex(toLabel);

        if (fromVertex == null || toVertex == null) {
            throw new Exception("The vertex/vertices were not found!");
        }

        return fromVertex.getEdgeWeight(toVertex);
    }

    //get the adjacent vertices of a vertex
    public DSALinkedList getAdjacent(Object label) throws Exception {
        DSAGraphNode vertex = findVertex(label);
        DSALinkedList result = null;

        if (vertex != null) 
        {
            DSALinkedList adjacentList = new DSALinkedList();
            for(DSAGraphNode v : vertex.getAdjacentVertex()) {
                adjacentList.insertLast(v);
            }
            result = adjacentList;
        }

        return result;
    }

    //check if a vertex is adjacent to another vertex
    public boolean isAdjacent(Object label1, Object label2) throws Exception {
        DSAGraphNode vertex1 = findVertex(label1);
        DSAGraphNode vertex2 = findVertex(label2);
        boolean adjacent = false;

        if (vertex1 != null && vertex2 != null) 
        {
            for (DSAGraphNode v : vertex1.getAdjacentVertex()) {
                if (v.getLabel().equals(label2)) {
                    adjacent = true;
                }
            }
        }
        
        return adjacent;
    }

    //display the graph in list format
    public void displayAsList() throws Exception {
        for (int i = 0; i < vertices.getCount(); i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            System.out.print(v.getLabel() + ": ");
            
            DSAGraphNode[] adjacent = v.getAdjacentVertex();
            double[] weights = v.getEdgeWeight();

            //print the adjacent vertices and weights
            for (int j = 0; j <adjacent.length; j++) {
                System.out.print(adjacent[j].getLabel() + "(" + weights[j] + "), "); 
            }
            System.out.println("\n");
        }
    }

    //display the graph in a matrix format
    public void displayAsMatrix() throws Exception {
        int size = vertices.getCount();
        Object[] labels = new Object[size];

        //get all the labels
        for (int i = 0; i < size; i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            labels[i] = v.getLabel();
        }

        System.out.print("     ");
        //print the matrix header
        for (int i = 0; i < size; i++) {
            System.out.print(labels[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            System.out.print(v.getLabel() + "   ");

            for (int j = 0; j < size; j++) {
                if (isAdjacent(v.getLabel(), labels[j])) {
                    double weight = getEdgeWeight(v.getLabel(), labels[j]);
                    System.out.print(weight + " ");
                }
                else {
                    System.out.print("0 ");
                }
            }
            System.out.println();
        }
    }

    //method to find a vetex by label
    private DSAGraphNode findVertex(Object label) throws Exception {
        DSAGraphNode foundVertex = null;
        
        for (int i = 0; i < vertices.getCount(); i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            if (v.getLabel().equals(label)) {
                foundVertex = v;
            }
        }
        return foundVertex;
    }

    //Breadth-first Search that prints levels
    public void bfs() throws Exception {
        DSAQueue queue = new DSAQueue(); //hold vertices to visit

        //Exception incase if the graph is empty
        if (vertices.getCount() == 0) {
            throw new Exception("Graph Empty");
        }

        //mark all vertices unvisited 
        for (int i = 0; i < vertices.getCount(); i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            v.clearVisited(); //sets visited to false
        }

        //Start from first vertex
        DSAGraphNode startV = (DSAGraphNode)vertices.getAt(0);
        startV.setVisited(); //mark visted

        BFSNode startN = new BFSNode(startV, 0); //create a BFS node with level 0
        queue.enqueue(startN); //vertex to queue

        System.out.println("BFS Results:");
        System.out.println("Level 0: " + startV.getLabel());

        int currlevel = 0; //to track current level being printed

        while (!queue.isEmpty()) {
            BFSNode currBFSNode = (BFSNode)queue.dequeue();
            DSAGraphNode currentV = currBFSNode.getNode();
            int level = currBFSNode.getLevel();

            //get all the unvisited adjacent vertices 
            DSALinkedList unvisited = new DSALinkedList();
            DSAGraphNode[] adjacent = currentV.getAdjacentVertex();

            for (int j = 0; j < adjacent.length; j++) {
                DSAGraphNode adjacentV = adjacent[j];
                if(!adjacentV.getVisited()) {
                    unvisited.insertLast(adjacentV);
                }
            }

            //Sort in alphabetical order (using bubble sort)
            for (int i = 0; i < unvisited.getCount() - 1; i++) {
                for (int j = i + 1; j < unvisited.getCount(); j++) {
                    DSAGraphNode vI = (DSAGraphNode)unvisited.getAt(i);
                    DSAGraphNode vJ = (DSAGraphNode)unvisited.getAt(j);

                    if (vI.getLabel().toString().compareTo(vJ.getLabel().toString()) > 0) {
                        DSAGraphNode temp = vI;
                        unvisited.setAt(i, vJ);
                        unvisited.setAt(j, temp);
                    }
                }
            }

            //process the vertices
            for (int i = 0; i < unvisited.getCount(); i++) {
                DSAGraphNode v = (DSAGraphNode)unvisited.getAt(i);
                v.setVisited(); //mark visited

                //Create node with incremented levels
                BFSNode newBFSNode = new BFSNode(v, level + 1);
                queue.enqueue(newBFSNode);

                //Tack the level display
                if (level + 1 > currlevel) {
                    if(currlevel > 0) {
                        System.out.println();
                    }
                    currlevel = level + 1;
                    System.out.print("Level " + currlevel + ": " + v.getLabel());
                }
                else {
                    System.out.print(", " + v.getLabel()); 
                }
            }
        }
    }

    //depth-first search with cycle detection
    public void dfs() throws Exception {
        //Exception incase if the graph is empty
        if (vertices.getCount() == 0) {
            throw new Exception("Graph Empty");
        }

        DSAStack stack = new DSAStack(); //stack to hold vertices
        DSAStack pathStack = new DSAStack(); //to track cycle detection
        DSALinkedList visited = new DSALinkedList(); //hold visited vertex
        boolean cycleFound  = false;
        DSALinkedList cycleVertices = new DSALinkedList(); //hold vertices in cycle

        //mark all vertices unvisited 
        for (int i = 0; i < vertices.getCount(); i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            v.clearVisited(); //sets visited to false
        }

        //Start from first
        DSAGraphNode startV = (DSAGraphNode)vertices.getAt(0);
        startV.setVisited(); //mark visited
        stack.push(startV);
        pathStack.push(null); //nothing before start
        visited.insertLast(startV.getLabel());

        while(!stack.isEmpty()) {
            DSAGraphNode currentV = (DSAGraphNode)stack.top();
            DSAGraphNode prevV = (DSAGraphNode)pathStack.top();

            DSAGraphNode nextUnvisited = null;
            DSAGraphNode[] adjacent = currentV.getAdjacentVertex();

            boolean found = false;
            //Chekc the cycles
            if (!cycleFound) {
                for (int j = 0; j < adjacent.length && !found; j++) {
                    DSAGraphNode adjacentV = adjacent[j];
                    if (adjacentV.getVisited() && (prevV == null || !adjacentV.getLabel().equals(prevV.getLabel()))) {
                        //Cycle detected
                        cycleFound = true;
                        cycleVertices.insertLast(currentV.getLabel());
                        cycleVertices.insertLast(adjacentV.getLabel());
                        found = true;
                    }
                }
            }

            found = false;
            //find the next unvisited vertex
            for (int j = 0; j < adjacent.length && !found; j++) {
                DSAGraphNode adjacentV = adjacent[j];
                if (!adjacentV.getVisited()) {
                    nextUnvisited = adjacentV;
                    found = true;
                }
            }

            if (nextUnvisited != null) {
                nextUnvisited.setVisited();
                stack.push(nextUnvisited);
                pathStack.push(currentV);
                visited.insertLast(nextUnvisited.getLabel());
            }
            else {
                stack.pop();
                pathStack.pop();
            }
        }

        //print the DFS result
        System.out.print("DFS Results: ");
        for (int i = 0; i < visited.getCount(); i++) {
            System.out.print(visited.getAt(i));
            if (i < visited.getCount() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();

        //print cycle detection
        if (cycleFound) {
            System.out.print("Cycle was detected with vertices: ");

            for (int i = 0; i < cycleVertices.getCount(); i++) {
                System.out.print(cycleVertices.getAt(i)); //print cycle vertices
                if (i < cycleVertices.getCount() - 1) { 
                    System.out.print(", ");
                }
            }

            System.out.println();
        }
        else {
            System.out.println("No cycle was found.");
        }
    }

    /*
     * Dijkstra's Shortest Path Algorithm
     * 
     * Purpose: To find the shortest path from a starting vertext to all other vertices in the graph/
     * 
     * Reference: Javaid, A. (2013, April 10). Understanding Dijkstra's Algorithm. Papers.ssm.com. 
     *     https://papers.ssrn.com/sol3/papers.cfm?abstract_id=2340905
     * 
     * Reference: GeeksforGeeks. (2012, November 25). Dijkstra's Algorithm to find Shortest Paths from a Source to all. GeeksforGeeks.
     *     https://www.geeksforgeeks.org/dsa/dijkstras-shortest-path-algorithm-greedy-algo-7/
     * 
     * Implementation: Used the reference to understand the algorithm and implement it without built-in functions and adapting to given scenario.
     */

    //Method to get minimum distance 
    private int minDistance(double[] distances, boolean[] visited, int n) {
        double min = 999999.0;
        int minIndex = -1;

        for (int v = 0; v < n; v++) {
            if (!visited[v] && distances[v] <= min) {
                min = distances[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

    //Method to get vertex index
    private int getVertexIndex(Object label) throws Exception {
        int result = -1;
        
        for (int i = 0; i < vertices.getCount(); i++) {
            DSAGraphNode v = (DSAGraphNode)vertices.getAt(i);
            if (v.getLabel().equals(label)) {
                result = i;
            }
        }

        return result;
    }

    public void dijkstras(Object sourceLabel, Object destLabel) throws Exception {
        //Exception incase if the graph is empty
        if (vertices.getCount() == 0) {
            throw new Exception("Graph Empty");
        }

        DSAGraphNode src = findVertex(sourceLabel);
        DSAGraphNode dest = findVertex(destLabel);

        if (src == null || dest == null) {
            throw new Exception("Source or destination not found");
        }

        int n = vertices.getCount();
        double[] distances = new double[n];
        boolean[] visited = new boolean[n];
        Object[] previous = new Object[n];

        //Initialise distance to a large amount 
        for (int i = 0; i < n; i++) {
            distances[i] = 999999.0;
            visited[i] = false;
            previous[i] = null; 
        }

        //set source to distance 0 
        int srcIndex = getVertexIndex(sourceLabel);
        distances[srcIndex] = 0.0; 

        boolean stop = false;

        //Main loop
        for (int count = 0; count < n - 1 && !stop; count++) {
            //Find minimum distance vertex 
            int u = minDistance(distances, visited, n);
            if (u == -1) {
                stop = true;
            }
            
            if (u != -1) {
                visited[u] = true;
                DSAGraphNode uVertex = (DSAGraphNode)vertices.getAt(u);

                //update distances of adjacent vertices 
                DSAGraphNode[] adjacent = uVertex.getAdjacentVertex();
                double[] weights = uVertex.getEdgeWeight();

                for (int i = 0; i < adjacent.length; i++) {
                    int v = getVertexIndex(adjacent[i].getLabel());

                    //v 
                    if (!visited[v] && distances[u] != 999999.0 && distances[u] + weights[i] < distances[v]) {
                        distances[v] = distances[u] + weights[i];
                        previous[v] = uVertex.getLabel();
                    }
                }
            }
        }

        //Print Results
        int destIndex = getVertexIndex(destLabel);
        System.out.println("Shortest Path starting from: " + sourceLabel + " to " + destLabel + ":");

        if(distances[destIndex] == 999999.0) {
            System.out.println("No path starting from: " + sourceLabel + " to " + destLabel);
        }
        else {
            //Reconstruct path
            DSAStack pathStack = new DSAStack();
            Object current = destLabel;

            while (current != null) {
                pathStack.push(current);
                int index = getVertexIndex(current);
                current = previous[index];
            }

            while (!pathStack.isEmpty()) {
                System.out.print(pathStack.pop());
                if (!pathStack.isEmpty()) {
                    System.out.print(" -> ");
                }
            }
            System.out.println("\nTotal travel time (min): " + distances[destIndex] + " minutes\n");
        }
    }

}
