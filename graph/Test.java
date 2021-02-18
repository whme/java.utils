package graph;


public class Test {
    public static void main(String[] args) {
        Graph g = new Graph("g");
        Vertex a = new Vertex(":a:");
        Vertex b = new Vertex(":b:");
        Vertex c = new Vertex(":c:");
        Vertex d = new Vertex(":d:");
        Vertex e = new Vertex(":e:");
        Vertex f = new Vertex(":f:");
        Vertex z = new Vertex(":z:");
        g.addEdge(a, b, 10);
        g.addEdge(a, c, 10);
        g.addEdge(b, c, 2);
        g.addEdge(b, d, 4);
        g.addEdge(b, e, 8);
        g.addEdge(c, e, 9);
        g.addEdge(d, f, 9);
        g.addEdge(e, f, 10);
        g.addEdge(e, d, 6);
        System.out.println(g.shortestPathBFS(a, z));
    }
}
