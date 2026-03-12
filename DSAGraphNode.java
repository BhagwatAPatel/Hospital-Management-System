public class DSAGraphNode {
    private Object label;
    private Object value;
    private boolean visited; //to track if the node has already been visited
    private DSAGraphNode[] adjacentVertex; //array to hold all vertices adjacent to this node
    private double[] edgeWeight; //array to hold the weights/travel times of edges 

    //Defualt constructor 
    public DSAGraphNode(Object inLabel, Object inValue) {
        label = inLabel;
        value = inValue;
        visited = false; //as no vertices have initially been visited
        adjacentVertex = new DSAGraphNode[0]; //as no vertices have been added yet
        edgeWeight = new double[0]; //no edges have been added yet
    }

    //get the label of the node 
    public Object getLabel() {
        return label;
    }

    //get the value of the node
    public Object getValue() {
        return value;
    }

    //get the visited status
    public boolean getVisited() {
        return visited;
    }

    //get the adjacent vertices of the node
    public DSAGraphNode[] getAdjacentVertex() {
        return adjacentVertex;
    }

    //get the edge weights of the node
    public double[] getEdgeWeight() {
        return edgeWeight; 
    }

    //get the wieght of an edge of a specific vertex
    public double getEdgeWeight(DSAGraphNode vertex) throws Exception {
        double weight = -1;
        
        for (int i = 0; i < adjacentVertex.length; i++) {
            if(adjacentVertex[i].getLabel() == vertex.getLabel()) {
                weight = edgeWeight[i];
            }
        }

        if (weight == -1) {
            throw new Exception("Vertex not found!");
        }

        return weight;
    }

    //add an edge to a vertex with a weight
    public void addEdge(DSAGraphNode vertex, double weight) {
        //add vertex to adjacentVertex array
        DSAGraphNode[] newAdjacents = new DSAGraphNode[adjacentVertex.length + 1];
        double[] newWeights = new double[edgeWeight.length + 1];

        //copy old values to new array
        for(int i = 0; i < adjacentVertex.length; i++) {
            newAdjacents[i] = adjacentVertex[i];
            newWeights[i] = edgeWeight[i];
        }

        newAdjacents[adjacentVertex.length] = vertex; //adds the new vertex
        newWeights[edgeWeight.length] = weight; //adds the new weight

        adjacentVertex = newAdjacents; //update the original array
        edgeWeight = newWeights; //update the original array
    }

    //set the visited status 
    public void setVisited() {
        visited = true;
    }

    //clear the visited status
    public void clearVisited() {
        visited = false;
    }

    public String toString() {
        String result = "Label: " + label + ", Value: " + value;
        return result;
    }

    //remove an edge
    public void removeEdge(DSAGraphNode vertex) throws Exception {
        int index = -1; //indicate "not found"
        boolean found = false; //to terminate loop when found

        for (int i = 0; i < adjacentVertex.length && !found; i++) {
            if (adjacentVertex[i].getLabel() == vertex.getLabel()) {
                index = i;
                found = true;
            }
        }

        if (!found) {
            throw new Exception("Vertex not found!");
        }

        if (index != -1) {
            //create arrays to temporarily store values
            DSAGraphNode[] newAdjacents = new DSAGraphNode[adjacentVertex.length - 1];
            double[] newWeights = new double[edgeWeight.length - 1];

            //copy all the values that aren't removed into the new arrays 
            for (int i = 0, j = 0; i < adjacentVertex.length; i++) {
                if (i != index) {
                    newAdjacents[j] = adjacentVertex[i];
                    newWeights[j] = edgeWeight[i];
                    j++;
                }
            }

            //update the main arryas
            adjacentVertex = newAdjacents;
            edgeWeight = newWeights;
        }
    }

    
}
