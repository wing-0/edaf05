import java.util.ArrayList;
import java.util.LinkedList;

public class spanning
{
	
	public static void main(String[] args)
	{
		
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