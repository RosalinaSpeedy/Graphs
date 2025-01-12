import java.util.ArrayList;

public class Vertex {
    public String name;
    public ArrayList<Edge> adjlist;
    public int distance;
    public String previous;

    public Vertex(String _name) {
    	this.name = _name;
    	this.adjlist = new ArrayList<Edge>();
    	this.distance = 0;
    	this.previous = null;
    }
}

