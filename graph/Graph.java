package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.NoSuchElementException;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;
    private String name;

    public Graph(String name) {
        this.name = name;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }


    public List<Edge> shortestPathBFS(Vertex start, Vertex target) {

        Queue<Vertex> queue = new LinkedList<>();
        List<Vertex> visited = new ArrayList<>();
        HashMap<Vertex, Vertex> parentNodes = new HashMap<>();

        queue.add(start);
        visited.add(start);
        while (true) {
            Vertex currentVertex = null;
            try {
                currentVertex = queue.remove();
            } catch (NoSuchElementException e) {
                throw new IllegalArgumentException("Target does not exist");
            }
            
            if (currentVertex.equals(target)) break;
            for (Edge edge : currentVertex.getOutgoingEdges()) {
                Vertex _currentVertex = edge.getSink();
                if (!visited.contains(_currentVertex)) {
                    queue.add(_currentVertex);
                    visited.add(_currentVertex);
                    parentNodes.put(_currentVertex, currentVertex);
                }
            }
        }

        List<Edge> result = new ArrayList<>();
        Vertex next_vertex, vertex = target;
        while (vertex != null) {
            next_vertex = parentNodes.get(vertex);
            if (next_vertex != null) {
                result.add(vertex.getInversePathTo(next_vertex));
            }
            vertex = next_vertex;
        }
        Collections.reverse(result);
        return result;
    }

    public void addEdge(Vertex source, Vertex sink, int capacity) {
        if (source.equals(sink)) {
            throw new IllegalArgumentException(
                "Source and sink are equal"
            );
        }
        if (this.edges.contains(new Edge(sink, source, capacity))) {
            throw new IllegalArgumentException("Inverse edge already exists in the graph");
        }

        if (!this.vertices.contains(source)) this.vertices.add(source);
        if (!this.vertices.contains(sink)) this.vertices.add(sink);

        Edge toBeAddedEdge = new Edge(source, sink, capacity);
        if (this.edges.contains(toBeAddedEdge)) {
            this.edges.get(edges.indexOf(toBeAddedEdge)).setCapacity(toBeAddedEdge.getCapacity());
            return;
        }
        this.edges.add(toBeAddedEdge);
        source.addOutgoingEdge(toBeAddedEdge);
        sink.addIncomingEdge(toBeAddedEdge);
    }

    @Override
    public String toString() {
        StringBuilder resultBuilder = new StringBuilder();
        for (Edge edge: this.edges) {
            resultBuilder.append(edge.toString());
            resultBuilder.append("\n");
        }
        String result = resultBuilder.toString();
        return result.substring(0, result.length() - 1);
    }
}
