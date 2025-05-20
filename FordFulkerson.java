import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

class FordFulkerson {

    private Set<Vertex> marked = null;
    private Map<Vertex, Edge> edgeTo = null;
    private int maxFlow = 0;

    /**
     * Implements the Ford-Fulkerson algorithm to compute the maximum flow in a
     * flow network. This constructor initializes the max-flow computation from 
     * source `s` to target `t`. It repeatedly finds augmenting paths and updates
     * the flow until no more augmenting paths exist.
     *
     * Algorithm Steps: 
     *   - While there is an augmenting path from `s` to `t`: 
     *   - Find the minimum residual capacity (bottleneck) along the path. 
     *   - Augment the flow along the path by this minimum value. 
     *   - Update the total max flow.
     *
     * @param s The source vertex of the flow network.
     * @param t The target (sink) vertex of the flow network.
     */
    public FordFulkerson(Vertex s, Vertex t) {

        while (hasAugmentingPath(s, t)) {
            int minValue = Integer.MAX_VALUE;

            // Find the bottleneck capacity in the augmenting path 
            // for (Vertex other : edgeTo.keySet()) {
            //     Edge edge = edgeTo.get(other);
            //     Vertex current = edge.getOther(other);
            //     // get residual capacity of a current vertex
            //     int residualCapacity = edge.getResidualCapacity(other);
            //     // if residualCapacity is less than minValue, update minValue
            //     if (residualCapacity < minValue) { minValue = residualCapacity; }
            // }
            for (Vertex v : edgeTo.keySet()) {
                Edge edge = edgeTo.get(v);
                Vertex other = edge.getOther(v);
                int residualCapacity = edge.getResidualCapacity(v);
                minValue = Math.min(minValue, residualCapacity);
            }

            // Augment the flow along the found path
            // for (Vertex other : edgeTo.keySet()) {
            //     Edge edge = edgeTo.get(other);
            //     // update flow
            //     edge.addResidualFlowTo(other, minValue);
            //     // update other flow
            //     for (int i = 0; i < other.getEdgeList().size(); i++) { 
            //         Edge otherEdge = other.getEdgeList().get(i);
            //         if (otherEdge.toString().substring(0, 2).equals(edge.toString().substring(0, 2))) {
            //             other.getEdgeList().remove(i);
            //             break;
            //         }
            //     }
            //     other.addEdge(edge);
            // }

            for (Vertex v = t; v != s; v = edgeTo.get(v).getOther(v)) {
                edgeTo.get(v).addResidualFlowTo(v, minValue);
                // update other flow
                for (int i = 0; i < v.getEdgeList().size(); i++) { 
                    Edge edge = v.getEdgeList().get(i);
                    if (edge.toString().substring(0, 2).equals(edgeTo.get(v).toString().substring(0, 2))) {
                        v.getEdgeList().remove(i);
                        break;
                    }
                }
                v.addEdge(edgeTo.get(v));
            }

            // Update the maximum flow value
            System.out.printf("MAX_FLOW: %d, ADDED: %d\n\n", maxFlow, minValue);
            maxFlow += minValue;
        }
    }

    /**
     * Checks whether a given vertex is part of the minimum cut set called 
     * marked. A vertex is in the minimum cut if it is reachable from the 
     * source in the final residual graph.
     *
     * @param v The vertex to check.
     * @return `true` if the vertex is in the minimum cut, `false` otherwise.
     */
    public boolean isInCut(Vertex v) {
        return marked.contains(v) ? true : false;
    }

    /**
     * Returns the computed maximum flow value after running the 
     * Ford-Fulkerson algorithm.
     *
     * @return The maximum flow from source `s` to target `t`.
     */
    public int getMaxFlow() {
        return maxFlow;
    }

    /**
     * Performs a breadth-first search (BFS) to find an augmenting path 
     * in the residual graph.
     *
     * This method marks vertices that are reachable from the source `s` 
     * via edges that have remaining capacity. It also tracks the path 
     * using the `edgeTo` map.
     *
     * @param s The source vertex.
     * @param t The target (sink) vertex.
     * @return `true` if there is an augmenting path from `s` to `t`, 
     * `false` otherwise.
     */
    private boolean hasAugmentingPath(Vertex s, Vertex t) {
        edgeTo = new HashMap<>(); // Stores edges in the augmenting path
        marked = new HashSet<>(); // Tracks visited vertices

        // Perform BFS to find an augmenting path 
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(s);
        marked.add(s);

        // while (queue is not empty and marked does not contain t)
        while (!queue.isEmpty() && !marked.contains(t)) { 
            // Loop through the polled vertex's edges
            Vertex current = queue.poll();
            
                // If the edge has residual capacity getResidualCapacity(w) and and marked does 
                // not contain the edge w. To get Vertex w, use getOther(polled vertex) on the edge.
                for (Edge edge : current.getEdgeList()) {
                    // Store the edge leading to this vertex
                    // Mark as visited (add target to visited)
                    // Add to queue for further exploration
                    Vertex other = edge.getOther(current);
                    int residualCapacity = edge.getResidualCapacity(other);
                    System.out.println(current + " to: " + other + " Edge: " + edge + " Capacity: " + residualCapacity + " Marked: " + marked);
                    if (residualCapacity > 0 && !marked.contains(other)) {
                        edgeTo.put(other, edge);
                        marked.add(other);
                        queue.add(other);
                        System.out.println("EdgeList: " + other.getEdgeList());
                        System.out.println("Queue: " + queue);
                        //break;
                    }
                }
        }
        // return true if `t` is in marked. This means an augmenting path was found
        System.out.println("Marked: " + marked + "\n" + "EdgeTo: " + edgeTo);
        return marked.contains(t);
    }
}
