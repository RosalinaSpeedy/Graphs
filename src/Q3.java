

public class Q3 {

	public static void main(String[] args) {
		Graph g = new Graph();
		String[] strSplit = "96955".split("");
		int moodleTotal = 0;
		for (String s : strSplit) {
			moodleTotal += Integer.parseInt(s);
		}
		int sNoTotal = 0;
		String[] strSplit2 = "33733570".split("");
		for (String s : strSplit2) {
			sNoTotal += Integer.parseInt(s);
		}
		System.out.println(sNoTotal);
		System.out.println(moodleTotal);
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addEdge("A", "D", 3);
		g.addEdge("B", "D", 28);
		g.addEdge("C", "A", 3);
		g.addEdge("A", "B", 99);
		g.addEdge("B", "C", 99);
		g.addEdge("D", "C", 99);
		//System.out.println(g.SPCost("A","B"));
		System.out.println(g.MSTCost());
	}

}
