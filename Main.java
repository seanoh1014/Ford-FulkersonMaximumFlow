import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filename = "data.txt";
        Map<String, Vertex> graph = getGraph(readData(filename));
        Vertex source = graph.get("s");
        Vertex sink = graph.get("t");

        FordFulkerson fordFulkerson = new FordFulkerson(source, sink);

        System.out.println("\nMaximum flow is: " + fordFulkerson.getMaxFlow());
        System.out.print("Vertices in the min cut set: ");
        for (Vertex v : graph.values()) {
            if (fordFulkerson.isInCut(v)) {
                System.out.print(v + " ");
            }
        }
        System.out.println("\n");
    }

    /**
     * Parses the input data and constructs a graph represented as a map of vertex 
     * names to Vertex objects. Each edge should be added to both v and w.
     *
     * @param data A list of strings representing edges in the graph.
     * @return A map of vertex names to Vertex objects representing the graph.
     */
    public static Map<String, Vertex> getGraph(List<String> data) {
        Map<String, Vertex> map = new HashMap<>();
        
        for (String current : data.get(0).split(" ")) {
            map.put(current, new Vertex(current));
        }

        for (int i = 1; i < data.size(); i++) {
            String[] arr = data.get(i).split(" ");
            map.get(arr[0]).addEdge(new Edge(map.get(arr[0]), map.get(arr[1]), Integer.parseInt(arr[2])));
            map.get(arr[1]).addEdge(new Edge(map.get(arr[0]), map.get(arr[1]), Integer.parseInt(arr[2])));
        }
        
        return map;
    }

    public static List<String> readData(String filename) {
        List<String> list = new ArrayList<>();
        try (Scanner input = new Scanner(new File(filename))) {
            while (input.hasNextLine()) {
                list.add(input.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
        }
        return list;
    }
}
