import java.util.ArrayList;
import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class spanning
{
	
	public static void main(String[] args)
	{
		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		String currentLine;
		Map<String,Integer> cities = new HashMap<String,Integer>();
		int i = 0;

		while(currentLine = in.readLine() != null 
			&& currentLine.charAt(currentLine.length()-1) != "]")
		{
			cities.put(currentLine, i)
		}
	}
	
	private LinkedList<Integer[]> prim(int[][] graph, int rootNode)
	{
		LinkedList<Integer[]> tree = new LinkedList<Integer[]>();
		ArrayList<Integer> nodesDone = new ArrayList<Integer>(graph.length);
		nodesDone.add(rootNode);
		ArrayList<Integer> nodesLeft = new ArrayList<Integer>(graph.length);
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
			int minNode = -1;
			int minDist = Integer.MAX_VALUE;
			for(int outNode : nodesLeft)
			{
				for(int inNode : nodesDone)
				{
					
				}
			}
		}
	}
	
}