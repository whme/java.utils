package graph;

public class Edge {
    private Vertex source;
    private Vertex sink;
    private int capacity;

    public Edge(Vertex source, Vertex sink, int capacity) {
        this.source = source;
        this.sink = sink;
        this.capacity = capacity;
    }

    public Edge(Vertex source, Vertex sink) {
        this(source, sink, -1);
    } 

    public Vertex getSink() {
        return sink;
    }

    public Vertex getSource() {
        return source;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return this.source.toString() + String.valueOf(this.capacity) + this.sink.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Edge other = (Edge) obj;

        if (other.getSource() == this.source && other.getSink() == this.sink) return true;

        return false;
    }
}
