import java.util.LinkedList;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class spanning
{
	
	public static void main(String[] args)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			String currentLine;
			ArrayList<String> cityNames = new ArrayList<String>();
			HashMap<String, Integer> cities = new HashMap<String, Integer>();
			int i = 0;
			currentLine = in.readLine();
			
			while (currentLine != null && currentLine.charAt(currentLine.length() - 1) != ']') 
			{
				// havent dealt with ""
				currentLine = currentLine.trim();
				cities.put(currentLine, i);
				cityNames.add(i, currentLine);
				i++;
				System.out.println(currentLine);
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
				System.out.println(currentLine);
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
			System.out.println("ha");
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
		for(int i = 0; i < graph.length; i++)
		{
			if(!nodesDone[i])
			{
				graph[i].updateMin(nodesDone);
				Q.add(graph[i]);
			}
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
				if(!nodesDone[l.endNode])
				{
					Node copyN = graph[l.endNode].copy();
					copyN.updateMin(nodesDone);
					Q.add(copyN);
//					System.out.print(l.endNode + " " + Q.peek().ID + ": " + Q.peek().minLength + ", ");
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
	LinkedList<Link> links;
	int minLength;
	int minNode;
	
	public Node(int ID)
	{
		this.ID = ID;
		this.links = new LinkedList<Link>();
		minLength = Integer.MAX_VALUE;
		minNode = -1;
	}
	
	public void updateMin(boolean[] nodesDone)
	{
		for(Link l : links)
		{
			if(nodesDone[l.endNode] && l.length < minLength)
			{
				minLength = l.length;
				minNode = l.endNode;
			}
		}
	}
	
	public Node copy()
	{
		Node copyN = new Node(ID);
		copyN.links = links;
		copyN.minLength = minLength;
		copyN.minNode = minNode;
		return copyN;
	}

	@Override
	public int compareTo(Node n)
	{
		return minLength - n.minLength;
	}
}

class Link
{
	
	int endNode;
	int length;
	
	public Link(int endNode, int length)
	{
		this.endNode = endNode;
		this.length = length;
	}
	
}