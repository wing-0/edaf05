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
	
//	public static void main(String[] args)
//	{
//		// Adjacency matrix for the tinyEWG-alpha
//		int[][] g = new int[8][8];
//		
//		g[4][5] = 35;
//		g[5][4] = 35;
//		g[4][7] = 37;
//		g[7][4] = 37;
//		g[5][7] = 28;
//		g[7][5] = 28;
//		g[0][7] = 16;
//		g[7][0] = 16;
//		g[1][5] = 32;
//		g[5][1] = 32;
//		g[0][4] = 38;
//		g[4][0] = 38;
//		g[2][3] = 17;
//		g[3][2] = 17;
//		g[1][7] = 19;
//		g[7][1] = 19;
//		g[0][2] = 26;
//		g[2][0] = 26;
//		g[1][2] = 36;
//		g[2][1] = 36;
//		g[1][3] = 29;
//		g[3][1] = 29;
//		g[2][7] = 34;
//		g[7][2] = 34;
//		g[6][2] = 40;
//		g[2][6] = 40;
//		g[3][6] = 52;
//		g[6][3] = 52;
//		g[6][0] = 58;
//		g[0][6] = 58;
//		g[6][4] = 93;
//		g[4][6] = 93;
//		
//		for(int i = 0; i < g.length; i++)
//		{
//			for(int k = 0; k < g[i].length; k++)
//			{
//				System.out.print(g[i][k] + " ");
//			}
//			System.out.println();
//		}
//		
//		LinkedList<Integer[]> tree = prim(g,3);
//		for(Integer[] i : tree)
//		{
//			System.out.print(i[0] + "--" + i[1] + "; ");
//		}
//		System.out.println("\n" + tree.size());
//		
//	}
	
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
			nodesLeft.remove(new Integer(minOutNode));
			nodesDone.add(minOutNode);

			tree.add(new Integer[]{minInNode, minOutNode});
		}
		return tree;
	}
	
}