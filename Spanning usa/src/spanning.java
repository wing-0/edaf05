import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class spanning
{
	
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		String currentLine;
		HashMap<String,Integer> cities = new HashMap<String,Integer>();
		int i = 0;

		while(currentLine = in.readLine() != null)
		{
			if(currentLine.charAt(currentLine.length()-1) != "]")
			{
				// havent dealt with ""
				cities.put(currentLine, i);	
				i++;
			} else 
			{
				
			}
		}
	}
	
	private LinkedList<Integer[]> prim(int[][] graph, int rootNode)
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
					if(graph[outNode][inNode] < minDist)
					{
						minInNode = inNode;
						minOutNode = outNode;
						minDist = graph[outNode][inNode];
					}
				}
			}
			
			// Maybe change nodesLeft to an array of booleans instead since nodes need
			// to be removed in the stage that follows here...
			// - Changed to LinkedList since the solution above would have been a problem
			// for the check for nodesLeft to be empty in every while iteration

			nodesLeft.remove(minOutNode);
			nodesDone.add(minOutNode);

			tree.add(new int[]{minInNode, minOutNode});

		}
	}
	
}