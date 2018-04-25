import java.util.*;
import java.io.*;

class PrimsAlgorithmMST {

	/*
	* SPOJ Problems:
	* - CSTREET
	* - MST
	*/

	/* 
	* Prim's Algorithm is used to find the minimum spanning tree for a connect
	* weighted undirected graph. This means it finds a subset of the edges that
	* forms a tree that includes every vertex, where the total weight of all the
	* edges in the tree is minimized. Initialize a tree with a single vertex,
	* chosen arbitrarily from the graph. Grow the tree by one edge: of the edges
	* that connect the tree to vertices not yet in the tree, find the minimum-weight
	* edge, and transfer it to the tree. Repeat this until all vertices are in the tree.
	*/

	public static int PrimsAlgorithm(Vertex[] graph, int source) {
		// set the initial cost of the minimum spanning tree to 0
		int cost = 0;
		// initialize an array that will keep track of which vertices have been visited
		boolean[] visited = new boolean[graph.length];
		// initialize a PriorityQueue that will keep track of the possible edges that
		// we can add to the tree we are forming, and will allow us to select the 
		// edge of least cost every step of the way
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<Edge>();
		// mark the initial vertex as visited
		visited[source] = true;

		// for every edge connected to the source, add it to the PriorityQueue
		for (Edge edge : graph[source].neighbors) {
			priorityQueue.add(edge);
		}

		// keep adding edges until the PriorityQueue is empty
		while (!priorityQueue.isEmpty()) {
			Edge e = priorityQueue.remove();

			// if we have already visited the opposite vertex, go to the next edge
			if (visited[e.end]) {
				continue;
			}

			// mark the opposite vertex as visited
			visited[e.end] = true;
			// increment the cost by the cost of the edge we are adding to the graph
			cost += e.cost;

			// for every edge connected to the opposite vertex, add it to the PriorityQueue
			for (Edge neighbor : graph[e.end].neighbors) {
				priorityQueue.add(neighbor);
			}
		}

		// if we haven't visited all of the vertices, return -1
		for (int i = 1; i < graph.length; i++) {
			if (!visited[i]) {
				return -1;
			}
		}

		// return the cost of the minimum spanning tree
		return cost;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
		int i = 0;
		String currentLine;
		HashMap<String, Integer> cities = new HashMap<String, Integer>();
		currentLine = in.readLine();
		while (currentLine.charAt(currentLine.length() - 1) != ']') 
		{
			// havent dealt with ""
			currentLine = currentLine.trim();
			cities.put(currentLine, i);
			i++;
			currentLine = in.readLine();
		}
		Vertex[] graph = new Vertex[i];
		
		for (int ii = 0; ii < i; ii++) {
			graph[ii] = new Vertex();
		}
		while (currentLine != null && currentLine.charAt(currentLine.length() - 1) == ']') 
		{
			String[] temp1 = currentLine.split("--");
			int city1 = cities.get(temp1[0]);
			String[] temp2 = temp1[1].split(" \\[");
			int city2 = cities.get(temp2[0]);
			int dist = Integer.parseInt(temp2[1].substring(0, temp2[1].length() - 1));
			graph[city1].neighbors.add(new Edge(city2, dist));
			graph[city2].neighbors.add(new Edge(city1, dist));
			currentLine = in.readLine();
		}

			int cost = PrimsAlgorithm(graph, 1);

			if (cost == -1) {
				System.out.printf("Campus #%d: I'm a programmer, not a miracle worker!\n", i);
			} else {
			 	System.out.printf("Campus #%d: %d\n", i, cost);	
			 }
	}

	static class Vertex {
		List<Edge> neighbors;

		public Vertex() {
			neighbors = new ArrayList<Edge>();
		}
	}

	static class Edge implements Comparable<Edge> {
		int end, cost;

		public Edge(int end, int cost) {
			this.end = end;
			this.cost = cost;
		}

		public int compareTo(Edge e) {
			return this.cost - e.cost;
		}
	}
}
