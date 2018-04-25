import java.util.LinkedList;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;

public class spanning2
{
	
	public static void main(String[] args)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			String currentLine;
			HashMap<String, Integer> cities = new HashMap<String, Integer>();
			HashMap<Integer, LinkedList<Integer[]>> adjList = new HashMap<Integer, LinkedList<Integer[]>>();
			int i = 0;
			
			while ((currentLine = in.readLine()) != null && currentLine.charAt(currentLine.length() - 1) != ']') 
			{
				// havent dealt with ""
				currentLine = currentLine.trim();
				cities.put(currentLine, i);
				adjList.put(i, new LinkedList<Integer[]>());
				i++;
			}
			
			while ((currentLine = in.readLine()) != null && currentLine.charAt(currentLine.length() - 1) == ']') 
			{
				String[] temp1 = currentLine.split("--");
				int city1 = cities.get(temp1[0]);
				String[] temp2 = temp1[1].split(" \\[");
				int city2 = cities.get(temp2[0]);
				int dist = Integer.parseInt(temp2[1].substring(0, temp2[1].length() - 1));
				adjList.get(city1).add({city2, dist});
			}
			in.close();
			LinkedList<Integer[]> tree = prim(adjList, 0);
			int len = 0;
			for(Integer[] link : tree)
			{
				len += link[2];
				//System.out.print(link[0] + "--" + link[1] + ": " + link[2] + "; ");
			}
			System.out.println("\n" + len);
			
		}
		catch (IOException e) 
		{
			System.out.println("File I/O error!");
			e.printStackTrace();
		}		
	}
	
	// Adjacency list: HashMap with node # as key, LinkedList<Integer[]> as value.
	// The LinkedList contains Integer[] where [0] is node # and [1] is weight
	
	private static LinkedList<Integer[]> prim(HashMap<Integer,LinkedList<Integer[]>> graph, int graphSize, int rootNode)
	{
		LinkedList<Integer[]> tree = new LinkedList<Integer[]>();
		boolean[] nodesDone = new boolean[graphSize];
		nodesDone[rootNode] = true;
		int numberDone = 1;
		PriorityQueue<node> Q = new PriorityQueue<node>(new nodeComparator());
		for(int i = 0; i < graphSize; i++)
		{
			if(!nodesDone[i])
			{
				Q.add(new node(i, graph.get(i), nodesDone));
			}
		}
		
		while(numberDone < graphSize)
		{
			
			node nextNode = Q.poll();
			nodesDone[nextNode.ID()] = true;
			tree.add(new Integer[]{nextNode.minNode(), 
					nextNode.ID(), nextNode.minDist()});
			numberDone++;
			
			for(int i = 0; i < graphSize; i++)
			{
				if(!nodesDone[i])
				{
					Q.add(new node(i, graph.get(i), nodesDone));
				}
			}
		}
		return tree;
	}
	
}

class node
{
	private int nodeID;
	private LinkedList<Integer[]> links;
	private int minDist;
	private int minNode;
	
	public node(int nodeID, LinkedList<Integer[]> links, boolean[] nodesDone)
	{
		this.nodeID = nodeID;
		minDist = Integer.MAX_VALUE;
		minNode = -1;
		for(Integer[] e : links)
		{
			this.links.add(e.clone());
			if(nodesDone[e[0]] && e[1] < minDist)
			{
				minNode = e[0];
				minDist = e[1];
			}
		}
	}
	
	public int ID()
	{
		return nodeID;
	}
	
	public int minNode()
	{
		return minNode;
	}
	
	public int minDist()
	{
		return minDist;
	}
	
}

class nodeComparator implements Comparator<node>
{
	@Override
	public int compare(node n1, node n2) {
		if(n1.minDist() < n2.minDist())
		{
			return -1;
		}
		else if(n1.minDist() > n2.minDist())
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	
}