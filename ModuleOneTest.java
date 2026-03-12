public class ModuleOneTest {
    public static void main(String[] args) {
        try {
            DSAGraph hospital = new DSAGraph();

            //Add 8 departments 
            System.out.println("Adding 9 Test Departments:\n");
            hospital.addVertex("Emergency", "Emergency Department");
            hospital.addVertex("ICU", "Intensive Care Unit");
            hospital.addVertex("Pharmacy", "Pharmacy Department");
            hospital.addVertex("Radiology", "Radiology Department");
            hospital.addVertex("Laboratory", "Laboratory Department");
            hospital.addVertex("OT", "Operating Theatres");
            hospital.addVertex("Wards", "Patient Wards");
            hospital.addVertex("Outpatient", "Outpatient Department");
            hospital.addVertex("Isolation", "Isolation Department"); //testing isolated departments

            System.out.println("Number of Departments added: " + hospital.getVertexCount());

            //Add corridors 
            System.out.println("Connecting Departments with 12 Corridors:\n");
            hospital.addEdge("Emergency", "ICU", 3.5);
            hospital.addEdge("Emergency", "Radiology", 5.0);
            hospital.addEdge("ICU", "Pharmacy", 2.5);
            hospital.addEdge("ICU", "OT", 4.0);
            hospital.addEdge("Pharmacy", "Emergency", 3.0);
            hospital.addEdge("Pharmacy", "Laboratory", 2.0);
            hospital.addEdge("Radiology", "Laboratory", 4.5);
            hospital.addEdge("Laboratory", "OT", 3.0);
            hospital.addEdge("OT", "Wards", 2.5);
            hospital.addEdge("Wards", "Outpatient", 3.5);
            hospital.addEdge("Radiology", "Outpatient", 6.0);
            hospital.addEdge("Laboratory", "Wards", 3.5);

            System.out.println("Number of Corridors: " + hospital.getEdgeCount() + "\n");

            //Displaying floorplan as list
            System.out.println("-----Hospital as List-----");
            hospital.displayAsList();
            System.out.println("\n");

            //Displaying bfs
            hospital.bfs();
            System.out.println("\n");

            //Displaying dfs
            hospital.dfs();;
            System.out.println("\n");

            //Testing Dijkstra's
            System.out.println("From Emergency to OT");
            hospital.dijkstras("Emergency", "OT");
            System.out.println();

            System.out.println("From Emergency to OutPatient");
            hospital.dijkstras("Emergency", "Outpatient");
            System.out.println();

            System.out.println("From Radiology to Wards");
            hospital.dijkstras("Radiology", "Wards");
            System.out.println();

            //Removing ICU
            System.out.println("Removing ICU\n");
            hospital.removeVertex("ICU");

            //Displaying floorplan as list
            System.out.println("-----Hospital as List After Removing ICU-----");
            hospital.displayAsList();
            System.out.println("\n");

            //Testing Dijkstra's
            System.out.println("From Emergency to OT (After Removing ICU)");
            hospital.dijkstras("Emergency", "OT");
            System.out.println();

            //Removing Corriodor
            System.out.println("Removing corridor between radiology and outpatient\n");
            hospital.removeEdge("Radiology", "Outpatient");

            //Displaying floorplan as list
            System.out.println("-----Hospital as List After Removing Edge-----");
            hospital.displayAsList();
            System.out.println("\n");

            //Testing Dijkstra's
            System.out.println("From Emergency to OutPatient (After removing corridor)");
            hospital.dijkstras("Emergency", "Outpatient");
            System.out.println();

            //Displaying bfs
            hospital.bfs();
            System.out.println("\n");

            //Displaying dfs
            hospital.dfs();;
            System.out.println("\n");

            //Test Isolated Department
            System.out.println("-----Testing Isolated Department-----");
            System.out.println("Checking Adjacency to Emergency: " + hospital.isAdjacent("Isolation", "Emergency"));
            System.out.println("Checking Adjacency to All: " + hospital.getAdjacent("Isolation").getCount() + " connections.");
            System.out.println();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
