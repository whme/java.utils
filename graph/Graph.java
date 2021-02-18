package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Graph {
    private List<Vertex> vertices;
    private List<Edge> edges;
    private String name;

    public Graph(String name) {
        this.name = name;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();
    }


    public ArrayList<Vertex> doBFSShortestPath(Vertex start, Vertex target) {
        ArrayList<Vertex> shortestPathList = new ArrayList<>();
		HashMap<Vertex, Boolean> visited = new HashMap<>();

		Queue<Vertex> queue = new LinkedList<>();
		Stack<Vertex> pathStack = new Stack<>();

		queue.add(start);
		pathStack.add(start);
		visited.put(start, true);

		while(!queue.isEmpty())
		{
			Vertex u = queue.poll();
			List<Edge> adjList = u.getOutgoingEdges();

			for(Edge e : adjList)
			{
                Vertex v = e.getSink();
				if(!visited.containsKey(v))
				{
					queue.add(v);
					visited.put(v, true);
					pathStack.add(v);
					if(u.equals(target))
						break;
				}
			}
		}


		//To find the path
		Vertex node, currentSrc=target;
		shortestPathList.add(target);
		while(!pathStack.isEmpty())
		{
			node = pathStack.pop();
            if (currentSrc.getIncomingEdges().contains(new Edge(node, currentSrc)))
			{
				shortestPathList.add(node);
				currentSrc = node;
				if(node.equals(start))
					break;
			}
		}

		return shortestPathList;
    }

    public List<Edge> shortestPathBFS(Vertex start, Vertex target) {

        Queue<Vertex> queue = new LinkedList<>();
        List<Vertex> visited = new ArrayList<>();
        HashMap<Vertex, Vertex> parentNodes = new HashMap<>();

        queue.add(start);
        visited.add(start);
        while (true) {
            Vertex currentVertex = queue.remove();
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
        List<Vertex> shortestPath = new ArrayList<>();
        Vertex vertex = target;
        while (vertex != null) {
            shortestPath.add(vertex);
            vertex = parentNodes.get(vertex);
        }

        List<Edge> result = new ArrayList<>();
        for (Vertex _vertex : shortestPath) {
            int index = shortestPath.indexOf(_vertex);
            if (index != shortestPath.size() -1) {
                result.add(_vertex.getInversePathTo(shortestPath.get(index+1)));
            }
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
