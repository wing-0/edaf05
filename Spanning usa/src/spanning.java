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
	}
	
}