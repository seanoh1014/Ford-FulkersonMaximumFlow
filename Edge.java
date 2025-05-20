public class Edge {

    private Vertex v; // from Vertex
    private Vertex w; // to Vertex
    private final int capacity;
    private int flow;

    public Edge(Vertex v, Vertex w, int capacity) {
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        flow = 0;
    }

    /*
     * Method should return the other Vertex in the edge. For example
     * if vertex equals v then the method should return w.
     */
    public Vertex getOther(Vertex vertex) {
        return vertex.equals(v) ? w : v;
    }

    /**
     * Calculates the residual capacity of the edge in the direction of the
     * given vertex. Residual capacity determines how much additional flow 
     * can be sent through this edge. It is used in the Ford-Fulkerson 
     * algorithm to find augmenting paths.
     *
     * @param vertex The vertex for which the residual capacity is being
     * checked.
     * @return The residual capacity available in the given direction.
     *
     * If the vertex is the source (v) of the edge (backward direction), 
     *   - The residual capacity is equal to the current flow (this allows 
     *     flow to be undone if necessary). 
     * If the vertex is the target (w) of the edge (forward direction), 
     *   - The residual capacity is calculated as (capacity - flow),
     *     meaning how much additional flow can be sent.
     *
     * Example Usage: Suppose an edge A → B has: 
     *   - Capacity = 10 
     *   - Current flow = 6
     *
     *   Calling getResidualCapacity(A) returns 6 (backward edge). 
     *   Calling getResidualCapacity(B) returns 4 (forward edge).
     *
     * This method is essential for determining available augmenting paths 
     * in the max-flow algorithm.
     */
    public int getResidualCapacity(Vertex vertex) {
        return vertex.equals(v) ? flow : (capacity - flow);
    }

    /**
     * Updates the flow along the edge by adding or subtracting the specified
     * delta flow. This method is used in the Ford-Fulkerson algorithm to adjust 
     * the flow in both forward and backward directions within the residual network.
     *
     * @param vertex The vertex indicating the direction of flow adjustment.
     * @param deltaFlow The amount of flow to be added or removed.
     *
     * Behavior: 
     *   - If the vertex is the source (v) of the edge (backward direction),
     *     the flow is reduced (`flow -= deltaFlow`). 
     *   - This represents pushing flow back, effectively undoing previous flow 
     *     allocation. 
     *   - If the vertex is the target (w) of the edge (forward direction), the flow 
     *     is increased (`flow += deltaFlow`). 
     *   - This represents sending additional flow along the edge.
     *
     * Example Usage: Suppose an edge A → B has: 
     *   - Capacity = 10 
     *   - Current flow = 6
     *
     *   Calling addResidualFlowTo(A, 3) (backward edge) updates flow to 3.
     *   Calling addResidualFlowTo(B, 2) (forward edge) updates flow to 8.
     *
     * Note: This method does not check whether the resulting flow exceeds
     * capacity or goes negative. Such validations should be handled 
     * externally before calling this method.
     */
    public void addResidualFlowTo(Vertex vertex, int deltaFlow) {
        flow += vertex.equals(v) ? -deltaFlow : deltaFlow;
    }

    public String toString() {
        return v + "-" + w + " " + flow + "/" + capacity;
    }
}
