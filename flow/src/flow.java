import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

public class flow {
	
	public static void main(String[] args)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			String currentLine;
			int nbrNodes = Integer.parseInt(in.readLine().trim());
			String[] nodeNames = new String[nbrNodes];
			
			for(int i = 0; i < nbrNodes; i++)
			{
				nodeNames[i] = in.readLine().trim();
			}
			
			int nbrEdges = Integer.parseInt(in.readLine().trim());
			int[][] capacities = new int[nbrNodes][nbrNodes];
			int[][] flows = new int[nbrNodes][nbrNodes];
			LinkedList<Edge>[] resList = new LinkedList[nbrNodes];
			for(int i = 0; i < resList.length; i++)
			{
				resList[i] = new LinkedList<Edge>();
			}
			for(int i = 0; i < nbrEdges; i++)
			{
				currentLine = in.readLine().trim();
				String[] e = currentLine.split("\\s+");
				int start = Integer.parseInt(e[0]);
				int end = Integer.parseInt(e[1]);
				int cap = Integer.parseInt(e[2]);
				cap = cap == -1 ? Integer.MAX_VALUE : cap;
				resList[start].add(new Edge(start,end,cap));
				capacities[start][end] = cap;
			}
			
			in.close();
			FordFulkerson(resList, capacities, flows, 0, nbrNodes-1);
		
		}
		catch (IOException e) 
		{
			System.out.println("File I/O error!");
			e.printStackTrace();
		}
	}
	
	public static void FordFulkerson(LinkedList<Edge>[] resList, int[][] capacities,
			int[][] flows, int start, int end)
	{
		Integer[] path = BFS(resList, start, end);
		System.out.println(Arrays.toString(path));
		while(path != null)
		{
			int delta = Integer.MAX_VALUE;
			for(int i = 0; i < path.length-1; i++)
			{
				delta = Math.min(delta, capacities[path[i]][path[i+1]] - 
						flows[path[i]][path[i+1]]);
			}
			
			for(int i = 0; i < path.length-1; i++)
			{
				// Regular graph
				flows[path[i]][path[i+1]] += delta;
				flows[path[i+1]][path[i]] -= delta;
				
				// Residual graph
				for(Edge e : resList[i])
				{
					if(e.end == path[i+1])
					{
						e.cap -= delta;
					}
				}
			}
			path = BFS(resList, start, end);
			System.out.println(Arrays.toString(path));
		}
		int maxflow = 0;
		for(int i = 0; i < flows[0].length; i++)
		{
			maxflow += flows[0][i];
		}
		System.out.println(maxflow);
	}
	
	public static Integer[] BFS(LinkedList<Edge>[] resList, int start, int end)
	{
		System.out.println("BFS!");
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(start);
		int[] pred = new int[resList.length];
		for(int i = 0; i < pred.length; i++)
		{
			pred[i] = -1;
		}
		while(!q.isEmpty())
		{
			int node = q.poll();
			for(Edge e : resList[node])
			{
				if(pred[e.end] == -1 && e.end != start && e.cap > 0)
				{
					q.add(e.end);
					pred[e.end] = e.start;
				}
			}
		}
		LinkedList<Integer> path = new LinkedList<Integer>();
		int node = end;
		while(node != start)
		{
			path.push(node);
			node = pred[node];
			if(node == -1)
			{
				return null;
			}
		}
		path.push(start);
		return path.toArray(new Integer[path.size()]);
	}
	
}

class Edge{
	
	int start,end,cap;
	
	public Edge(int start, int end,int cap)
	{
		this.start = start;
		this.end = end;
		this.cap = cap;
	}
	
}
