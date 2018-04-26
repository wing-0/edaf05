import java.util.LinkedList;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class spanning
{
	
	public static void main(String[] args)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			String currentLine;
//			ArrayList<String> cityNames = new ArrayList<String>();
			HashMap<String, Integer> cities = new HashMap<String, Integer>();
			int i = 0;
			currentLine = in.readLine();
			
			while (currentLine != null && currentLine.charAt(currentLine.length() - 1) != ']') 
			{
				// havent dealt with ""
				currentLine = currentLine.trim();
				cities.put(currentLine, i);
//				cityNames.add(i, currentLine);
				i++;
//				System.out.println(currentLine);
				currentLine = in.readLine();
			}
			
			Node[] adjList = new Node[i];
			for(int k = 0; k < i; k++)
			{
				adjList[k] = new Node(k);
			}
			
			while (currentLine != null && currentLine.charAt(currentLine.length() - 1) == ']') 
			{
				String[] temp1 = currentLine.split("--");
				int city1 = cities.get(temp1[0]);
				String[] temp2 = temp1[1].split(" \\[");
				int city2 = cities.get(temp2[0]);
				int dist = Integer.parseInt(temp2[1].substring(0, temp2[1].length() - 1));
				adjList[city1].links.add(new Link(city2, dist));
				adjList[city2].links.add(new Link(city1, dist));
//				System.out.println(currentLine);
				currentLine = in.readLine();
			}
			in.close();
			
//			for(int k = 0; k < i; k++)
//			{
//				System.out.print(cityNames.get(k) + ": ");
//				for(Integer[] e : adjList.get(k))
//				{
//					System.out.print(cityNames.get(e[0]) + " (" + e[1] + "), ");
//				}
//				System.out.println();
//			}
//			System.out.println("ha");
			LinkedList<Integer[]> tree = prim(adjList, i, 0);
			int len = 0;
			for(Integer[] link : tree)
			{
				len += link[2];
//				System.out.print(link[0] + "--" + link[1] + ": " + link[2] + "; ");
			}
			System.out.println(len);
			
		}
		catch (IOException e) 
		{
			System.out.println("File I/O error!");
			e.printStackTrace();
		}		
	}
	
	// Adjacency list: HashMap with node # as key, LinkedList<Integer[]> as value.
	// The LinkedList contains Integer[] where [0] is node # and [1] is weight
	
	private static LinkedList<Integer[]> prim(Node[] graph, int graphSize, int rootNode)
	{
		LinkedList<Integer[]> tree = new LinkedList<Integer[]>();
		boolean[] nodesDone = new boolean[graphSize];
		nodesDone[rootNode] = true;
		int numberDone = 1;
		PriorityQueue<Node> Q = new PriorityQueue<Node>();
		for(Link l : graph[rootNode].links)
		{
			boolean updated = false;
			int neigh = l.endNode;
			LinkedList<Link> polled = new LinkedList<Link>();
			while(!updated)
			{
				Link nodeL = graph[neigh].links.poll();
				polled.add(nodeL);
				if(nodeL.endNode == rootNode)
				{
					graph[neigh].minLength = nodeL.length;
					graph[neigh].minNode = nodeL.endNode;
					updated = true;
				}
			}
			graph[neigh].links.addAll(polled);
			Q.add(graph[l.endNode]);
		}
		
		while(numberDone < graphSize)
		{
			Node nextNode;
			do{
				nextNode = Q.poll();
			} while(nodesDone[nextNode.ID]);
			
			nodesDone[nextNode.ID] = true;
			tree.add(new Integer[]{nextNode.minNode, 
					nextNode.ID, nextNode.minLength});
			numberDone++;
//			System.out.println("Polled " + nextNode.ID);
			
			for(Link l : nextNode.links)
			{
				int neigh = l.endNode;
				if(!nodesDone[neigh])
				{
					boolean updated = false;
					LinkedList<Link> polled = new LinkedList<Link>();
					while(!updated)
					{
						Link nodeL = graph[neigh].links.poll();
						polled.add(nodeL);
						if(!nodesDone[neigh] && nodesDone[nodeL.endNode])
						{
							if(nodeL.length < graph[neigh].minLength)
							{
								Q.remove(graph[neigh]);
								graph[neigh].minLength = nodeL.length;
								graph[neigh].minNode = nodeL.endNode;
								graph[neigh].links.addAll(polled);
								Q.add(graph[neigh]);
							}
							else
							{
								graph[neigh].links.addAll(polled);
							}
							updated = true;
						}
					}
				}
			}
//			System.out.println("\n");
		}
		return tree;
	}
	
}

class Node implements Comparable<Node>
{
	int ID;
	PriorityQueue<Link> links;
	int minLength;
	int minNode;
	
	public Node(int ID)
	{
		this.ID = ID;
		this.links = new PriorityQueue<Link>();
		minLength = Integer.MAX_VALUE;
		minNode = -1;
	}

	@Override
	public int compareTo(Node n)
	{
		return minLength - n.minLength;
	}
}

class Link implements Comparable<Link>
{
	
	int endNode;
	int length;
	
	public Link(int endNode, int length)
	{
		this.endNode = endNode;
		this.length = length;
	}

	@Override
	public int compareTo(Link l) {
		return length - l.length;
	}
	
}