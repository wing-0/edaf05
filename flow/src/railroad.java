import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class railroad {
	
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
			int[][] flows = new int[nbrNodes][nbrNodes];
			int[][] residual = new int[nbrNodes][nbrNodes];
			for(int i = 0; i < nbrEdges; i++)
			{
				currentLine = in.readLine().trim();
				String[] e = currentLine.split("\\s+");
				int start = Integer.parseInt(e[0]);
				int end = Integer.parseInt(e[1]);
				int cap = Integer.parseInt(e[2]);
				cap = cap == -1 ? Integer.MAX_VALUE : cap;
				residual[start][end] = cap;
				residual[end][start] = cap;
			}
			in.close();
			FordFulkerson(residual, flows, 0, nbrNodes-1);
		
		}
		catch (IOException e) 
		{
			System.out.println("File I/O error!");
			e.printStackTrace();
		}
	}
	
	public static void FordFulkerson(int[][] residual, int[][] flows, 
			int start, int end)
	{
		Integer[] path = BFS(residual, start, end);
		while(path != null)
		{
			int delta = Integer.MAX_VALUE;
			for(int i = 0; i < path.length-1; i++)
			{
				delta = Math.min(delta, residual[path[i]][path[i+1]]);
			}
			
			for(int i = 0; i < path.length-1; i++)
			{
				// Regular graph
				flows[path[i]][path[i+1]] += delta;
				flows[path[i+1]][path[i]] -= delta;
				
				// Residual graph
				residual[path[i]][path[i+1]] -= delta;
				residual[path[i+1]][path[i]] += delta;
			}
			path = BFS(residual, start, end);
		}
		
		int maxflow = 0;
		LinkedList<Edge> minCut = new LinkedList<Edge>();
		int[] pred = new int[residual.length];
		BFS(residual, start, end, pred);
		for(int i = 0; i < flows.length; i++)
		{
			maxflow += flows[0][i];
			for(int k = 0; k < flows.length; k++)
			{
				if(pred[i] != -1 && pred[k] == -1 && flows[i][k] > 0)
				{
					minCut.add(new Edge(i,k));
				}
			}
		}
		System.out.println(maxflow);
//		for(Edge e : minCut)
//		{
//			System.out.println(e.start + " " + e.end + " " + flows[e.start][e.end]);
//		}
		
	}
	
	public static Integer[] BFS(int[][] residual, int start, int end)
	{
		return BFS(residual, start, end, new int[residual.length]);
	}
	
	public static Integer[] BFS(int[][] residual, int start, int end, int[] pred)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		q.add(start);
		for(int i = 0; i < pred.length; i++)
		{
			pred[i] = -1;
		}
		pred[0] = 0;
		while(!q.isEmpty())
		{
			int node = q.poll();
			for(int i = 0; i < residual.length; i++)
			{
				if(pred[i] == -1 && residual[node][i] > 0)
				{
					q.add(i);
					pred[i] = node;
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
	
	int start,end;
	
	public Edge(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	
}
