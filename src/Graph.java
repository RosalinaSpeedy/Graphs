import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {
	public ArrayList<Vertex> vlist;

	public Graph() {
		this.vlist = new ArrayList<Vertex>();
	}

	public void addVertex(String name) {
		Vertex v = new Vertex(name);
		this.vlist.add(v);
	}

	public Vertex getVertex(String name) {
		for (Vertex v : this.vlist) {
			if (v.name.equals(name)) {
				return v;
			}
		}
		return null;
	}

	public void addEdge(String from, String to, int weight) {
		System.out.println(from);
		Vertex v1 = this.getVertex(from);
		System.out.println(this.getVertex(from));
		Vertex v2 = this.getVertex(to);
		Edge e = new Edge(v1, v2, weight);
		v1.adjlist.add(e);
		if (!from.equals(to)) {
			v2.adjlist.add(e);
		}
	}

	public Edge getEdge(String from, String to) {
		Vertex v1 = this.getVertex(from);
		Vertex v2 = this.getVertex(to);
		try {
			for (Edge e : v1.adjlist) {
				if (e.from.equals(v1) && e.to.equals(v2)) {
					return e;
				}
			}
			for (Edge e : v1.adjlist) {
				if (e.from.equals(v2) && e.to.equals(v1)) {
					return e;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public int MSTCost() {
		Graph mst = this.MST();
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int total = 0;
		for (Vertex v : mst.vlist) {
			for (Edge e : v.adjlist) {
				boolean edgeAccountedFor = false;
				for (Edge e2 : edges) {
					if (e2.from.equals(e.from) && e2.to.equals(e.to)) {
						edgeAccountedFor = true;
					}
				}
				if (!edgeAccountedFor) {
					total += e.weight;
					edges.add(e);
				}
			}
		}
		return total;
	}

	public Graph MST() {
		ArrayList<Edge> edges = new ArrayList<Edge>();
		HashMap<Vertex, Boolean> connected = new HashMap<Vertex, Boolean>();
		System.out.println("Input tree:");
		for (Vertex v : this.vlist) {
			System.out.println(v.name);
			for (Edge e : v.adjlist) {
				System.out.println(e.from.name + " (" + e.weight + ") -> " + e.to.name);
			}
			connected.put(v, false);
		}
		Graph mst = new Graph();
		if (this.vlist.size() == 0) {
			System.out.println("found zero-based graph");
			return mst;
		}
		for (Vertex v : this.vlist) {
			mst.vlist.add(new Vertex(v.name));
		}
		if (mst.vlist.size() > 1) {
			for (int i = 0; i < this.vlist.size(); i++) {
				System.out.println("looking at vertex: " + this.vlist.get(i).name);
				Edge currentShortest = null;
				outeredgeloop: for (Edge e : this.vlist.get(i).adjlist) {
					for (Edge e2 : edges) {
						if (e.to.name.equals(e2.to.name) && e.from.name.equals(e2.from.name)) {
							System.out.println("found edge from " + e2.from.name + " to " + e2.to.name + ", skipping");
							continue outeredgeloop;
						} 
					}
					currentShortest = e;
				}
				if (currentShortest == null) {
					continue;
				}
				System.out.println(
						"current shortest init from " + currentShortest.from.name + " to " + currentShortest.to.name);
				shortedgeloop: for (Edge e : this.vlist.get(i).adjlist) {
					if (e.weight < currentShortest.weight && !e.to.name.equals(e.from.name)) {
						for (Edge e2 : edges) {
							if (e.to.name.equals(e2.to.name) && e.from.name.equals(e2.from.name)) {
								continue shortedgeloop;
							}
						}
						currentShortest = e;
						System.out.println("current shortest change: from " + currentShortest.from.name + " to "
								+ currentShortest.to.name);
					}
				}
				if (connected.get(currentShortest.from) && connected.get(currentShortest.to)) {
					System.out.println("both verticies accounted for; skipping");
					continue;
				}
				if (currentShortest.to.name.equals(currentShortest.from.name)) {
					System.out.println("found loop - skipping");
					continue;
				}
				System.out.println("added edge between " + currentShortest.from.name + " and " + currentShortest.to.name
						+ ", " + currentShortest.weight);
				mst.addEdge(currentShortest.from.name, currentShortest.to.name, currentShortest.weight);
				edges.add(new Edge(currentShortest.from, currentShortest.to, currentShortest.weight));
				connected.put(currentShortest.from, true);
				connected.put(currentShortest.to, true);
			}
		}
		System.out.println("MST:");
		for (Vertex v : mst.vlist) {
			System.out.println(v.name);
			for (Edge e : v.adjlist) {
				System.out.println(e.from.name + " (" + e.weight + ") -> " + e.to.name);
			}

		}
		return mst;
	}

	public int SPCost(String from, String to) {
		Graph SP = this.SP(from, to);
		ArrayList<Edge> edges = new ArrayList<Edge>();
		int total = 0;
		for (Vertex v : SP.vlist) {
			for (Edge e : v.adjlist) {
				boolean edgeAccountedFor = false;
				for (Edge e2 : edges) {
					if (e2.from.equals(e.from) && e2.to.equals(e.to)) {
						edgeAccountedFor = true;
					}
				}
				if (!edgeAccountedFor) {
					total += e.weight;
					edges.add(e);
				}
			}
		}
		return total;
	}

	public Graph SP(String from, String to) {
		System.out.println("Input graph:");
		for (Vertex v : this.vlist) {
			System.out.println(v.name);
			for (Edge e : v.adjlist) {
				System.out.println(e.from.name + " (" + e.weight + ") -> " + e.to.name);
			}
		}
		ArrayList<Vertex> explored = new ArrayList<Vertex>();
		Vertex origin = this.getVertex(from);
		System.out.println(origin.name);
		Vertex destination = this.getVertex(to);
		System.out.println(destination.name);
		HashMap<Vertex, Integer> weights = new HashMap<Vertex, Integer>();
		PriorityQueue<Vertex> q = new PriorityQueue<>((v1, v2) -> weights.get(v1) - weights.get(v2));
		for (Vertex v : this.vlist) {
			weights.put(v, -1);
		}
		Vertex currentVertex = origin;
		q.add(currentVertex);
		vertexloop: while (q.size() > 0) {
			currentVertex = q.poll();
			System.out.println("Current vertex is " + currentVertex.name);
			for (Vertex v : explored) {
				if (v.equals(currentVertex)) {
					System.out.println("Vertex " + currentVertex.name + " Has already been explored");
					continue vertexloop;
				}
			}
			for (Edge e : currentVertex.adjlist) {
				Vertex adjacent = null;
				if (e.to.name.equals(e.from.name)) {
					System.out.println("loop found");
					continue;
				}
				if (currentVertex.name.equals(e.from.name)) {
					adjacent = e.to;
					System.out.println("adjacent is " + e.to.name);
				} else {
					adjacent = e.from;
					System.out.println("adjacent is " + e.from.name);
				}
				q.add(adjacent);
				int distance = -1;
				if (weights.get(currentVertex) == -1) {
					distance = e.weight;
				} else {
					distance = weights.get(currentVertex) + e.weight;
				}
				if (currentVertex.name.equals(adjacent.name)) {
					continue;
				}
				System.out.println("distance to current vertex: " + distance);
				System.out.println("last recorded distance to current vertex: " + weights.get(adjacent));
				if (weights.get(adjacent) > distance || weights.get(adjacent) == -1) {
					weights.put(adjacent, distance);
					adjacent.previous = currentVertex.name;
					System.out.println(adjacent.previous + " comes before " + adjacent.name);
					adjacent.distance = e.weight;
					System.out.println(adjacent.distance + " is the distance to " + adjacent.name);
					q.add(adjacent);
					System.out.println("added " + adjacent.name + " to the previous verticies from " + currentVertex.name);
				}
			}
			explored.add(currentVertex);
			System.out.println(currentVertex.name + " has been explored");
			System.out.println(q.size());
		}
		Graph SP = null;
		if (to.equals(from)) {
			SP = new Graph();
			SP.addVertex(from);
			return SP;
		}
		if (weights.get(destination) != -1) {
			SP = new Graph();
			System.out.println(weights.get(destination));
			Vertex v = destination;
			SPloop: while (v.previous != null) {
				System.out.println("current: " + v.name);
				System.out.println("previous: " + v.previous);
				Vertex b = new Vertex(v.name);
				b.previous = v.previous;
				b.distance = v.distance;
				SP.vlist.add(b);
				for (Vertex v2 : explored) {
					System.out.println("name of previous:" + v.previous);
					System.out.println("have access to " + v2.name);
					if (v2.name.equals(v.previous)) {
						v = v2;
					} else if (v.name.equals(from)) {
						Vertex b1 = new Vertex(from);
						SP.vlist.add(b1);
						break SPloop;
					} else {
						System.out.println("couldnt find vertex, current name is: " + v.name);
						//break SPloop;
					}
				}
			}
			if (SP.vlist.size() > 1) {
				System.out.println("adding edges");
				for (Vertex v1 : SP.vlist) {
					System.out.println("analysing " + v1.name);
					try {
						for (Vertex v2 : SP.vlist) {
							System.out.println("comparing to " + v2.name);
							System.out.println(v2.previous);
							if (v2.name.equals(v1.previous)) {
								System.out.println("found equivilant prev");
								v1.adjlist.add(new Edge(v1, v2, v1.distance));
								v2.adjlist.add(new Edge(v1, v2, v1.distance));
							}
						}
						
					} catch (Exception e) {
						System.out.println("some error");
					}
				}
			}
		}
		System.out.println("SP graph:");
		for (Vertex v : SP.vlist) {
			System.out.println(v.name);
			for (Edge e : v.adjlist) {
				System.out.println(e.from.name + " (" + e.weight + ") -> " + e.to.name);
			}
		}
		return SP;
	}

	public static void main(String[] args) {
		Graph g = new Graph();
		g.addVertex("P");
		g.addVertex("Q");
		g.addVertex("R");
		g.addVertex("S");
		g.addVertex("T");
		g.addEdge("P", "P", 15);
		g.addEdge("P", "Q", 5);
		g.addEdge("P", "R", 2);
		g.addEdge("P", "S", 11);
		g.addEdge("P", "T", 13);
		g.addEdge("Q", "Q", 8);
		g.addEdge("Q", "R", 3);
		g.addEdge("Q", "S", 9);
		g.addEdge("Q", "T", 4);
		g.addEdge("R", "R", 1);
		g.addEdge("R", "S", 14);
		g.addEdge("R", "T", 12);
		g.addEdge("S", "S", 6);
		g.addEdge("S", "T", 7);
		g.addEdge("T", "T", 10);

		System.out.println(g.vlist.size());
		for (Vertex v : g.vlist) {
			//System.out.println(v.name);
		}
		for (Vertex v : g.SP("R", "S").vlist) {
			//System.out.println(v.name);
		}
	}
}
