package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private List<Edge> incomingEdges;
    private List<Edge> outgoingEdges;
    private String name;

    public Vertex(String name) {
        this.name = name;
        this.incomingEdges = new ArrayList<>();
        this.outgoingEdges = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addIncomingEdge(Edge edge) {
        this.incomingEdges.add(edge);
    }

    public List<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    public void addOutgoingEdge(Edge edge) {
        this.outgoingEdges.add(edge);
    }

    public List<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }

    public Edge getInversePathTo(Vertex vertex) {
        for (Edge edge: this.incomingEdges) {
            if (edge.getSource().equals(vertex)) return edge;
        }
        throw new IllegalArgumentException("Path doesn't exist");
    }

    public String printable() {
        return this.incomingEdges.toString() + " " + this.name + " " + this.outgoingEdges.toString();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
