import java.util.ArrayList;
import java.util.List;

class Vertex {
    private String name;
    private List<Edge> edgeList;
    public boolean marked = false;

    public Vertex(String name) {
        this.name = name;
        edgeList = new ArrayList<>();
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void addEdge(Edge e) {    
         edgeList.add(e);  
    }
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        Vertex other = (Vertex) obj;
        return this.name.equals(other.name);
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return this.name;
    }
}
