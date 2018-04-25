import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
			int i = 0;
			
			while ((currentLine = in.readLine()) != null && currentLine.charAt(currentLine.length() - 1) != ']') 
			{
				// havent dealt with ""
				currentLine = currentLine.trim();
				cities.put(currentLine, i);
				i++;
			}
			int[][] adjMat = new int[i][i];
			while ((currentLine = in.readLine()) != null && currentLine.charAt(currentLine.length() - 1) == ']') 
			{
				String[] temp1 = currentLine.split("--");
				String city1 = temp1[0];
				String[] temp2 = temp1[1].split(" \\[");
				String city2 = temp2[0];
				int dist = Integer.parseInt(temp2[1].substring(0, temp2[1].length() - 1));
				adjMat[cities.get(city1)][cities.get(city2)] = dist;
				adjMat[cities.get(city2)][cities.get(city1)] = dist;
			}
			in.close();
			LinkedList<Integer[]> tree = prim(adjMat, 0);
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
	
	private static LinkedList<Integer[]> prim(int[][] graph, int rootNode)
	{
		LinkedList<Integer[]> tree = new LinkedList<Integer[]>();
		LinkedList<Integer> nodesDone = new LinkedList<Integer>();
		nodesDone.add(rootNode);
		LinkedList<Integer> nodesLeft = new LinkedList<Integer>();
		for(int i = 0; i < graph.length; i++)
		{
			if(i != rootNode)
			{
				nodesLeft.add(i);
			}
		}
		
		while(!nodesLeft.isEmpty())
		{
			// Search for a node in nodesLeft which minimizes distance
			// to nodes in nodesDone
			int minInNode = -1;
			int minOutNode = -1;
			int minDist = Integer.MAX_VALUE;
			for(int outNode : nodesLeft)
			{
				for(int inNode : nodesDone)
				{
					if(graph[outNode][inNode] < minDist && graph[outNode][inNode] != 0)
					{
						minInNode = inNode;
						minOutNode = outNode;
						minDist = graph[outNode][inNode];
					}
				}
			}
			nodesLeft.remove(new Integer(minOutNode));
			nodesDone.add(minOutNode);
			tree.add(new Integer[]{minInNode, minOutNode, minDist});
		}
		return tree;
	}
	
	private class node
	{
		private int nodeID;
		private LinkedList<Integer[]> neighbors;
		
		public node(int nodeID, LinkedList<Integer[]> neighbors)
		{
			this.nodeID = nodeID;
			LinkedList
			for(Integer[] e : neighbors)
			{
				
			}
		}
	}
	
}