import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class gorilla {
	
	private static int[][] score;
	private static HashMap<Character, Integer> scoreInd;
	
	public static void main(String[] args)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			String currentLine;
			currentLine = in.readLine().trim();
			String[] nums = currentLine.split("\\s+");
			int nbrSpecies = Integer.parseInt(nums[0]);
			int nbrTests = Integer.parseInt(nums[1]);
			
			HashMap<String, String> species = new HashMap<String, String>();
			
			for(int i = 0; i<nbrSpecies; i++)
			{
				String speciesLine = in.readLine().trim();
				String seqLine = in.readLine().trim();
				species.put(speciesLine, seqLine);
			}
			
			String[][] tests = new String[nbrTests][2];
			for(int i = 0; i<nbrTests; i++)
			{
				currentLine = in.readLine().trim();
				tests[i] = currentLine.split("\\s+");
			}
			in.close();
			
			scoreInd = new HashMap<Character, Integer>();  
			int i = 0;
			char[] list = {'A', 'R', 'N', 'D', 'C', 'Q', 'E', 'G', 'H', 'I', 'L', 'K', 'M',
							'F', 'P', 'S', 'T', 'W', 'Y', 'V', 'B', 'Z', 'X', '*'};
			for(char key : list)
			{
				scoreInd.put(key, i);
				i++;
			}
				
			score = new int[][]{
					{4,-1,-2,-2,0,-1,-1,0,-2,-1,-1,-1,-1,-2,-1,1,0,-3,-2,0,-2,-1,0,-4},
					{-1,5,0,-2,-3,1,0,-2,0,-3,-2,2,-1,-3,-2,-1,-1,-3,-2,-3,-1,0,-1,-4},
					{-2,0,6,1,-3,0,0,0,1,-3,-3,0,-2,-3,-2,1,0,-4,-2,-3,3,0,-1,-4},
					{-2,-2,1,6,-3,0,2,-1,-1,-3,-4,-1,-3,-3,-1,0,-1,-4,-3,-3,4,1,-1,-4},
					{0,-3,-3,-3,9,-3,-4,-3,-3,-1,-1,-3,-1,-2,-3,-1,-1,-2,-2,-1,-3,-3,-2,-4},
					{-1,1,0,0,-3,5,2,-2,0,-3,-2,1,0,-3,-1,0,-1,-2,-1,-2,0,3,-1,-4},
					{-1,0,0,2,-4,2,5,-2,0,-3,-3,1,-2,-3,-1,0,-1,-3,-2,-2,1,4,-1,-4},
					{0,-2,0,-1,-3,-2,-2,6,-2,-4,-4,-2,-3,-3,-2,0,-2,-2,-3,-3,-1,-2,-1,-4},
					{-2,0,1,-1,-3,0,0,-2,8,-3,-3,-1,-2,-1,-2,-1,-2,-2,2,-3,0,0,-1,-4},
					{-1,-3,-3,-3,-1,-3,-3,-4,-3,4,2,-3,1,0,-3,-2,-1,-3,-1,3,-3,-3,-1,-4},
					{-1,-2,-3,-4,-1,-2,-3,-4,-3,2,4,-2,2,0,-3,-2,-1,-2,-1,1,-4,-3,-1,-4},
					{-1,2,0,-1,-3,1,1,-2,-1,-3,-2,5,-1,-3,-1,0,-1,-3,-2,-2,0,1,-1,-4},
					{-1,-1,-2,-3,-1,0,-2,-3,-2,1,2,-1,5,0,-2,-1,-1,-1,-1,1,-3,-1,-1,-4},
					{-2,-3,-3,-3,-2,-3,-3,-3,-1,0,0,-3,0,6,-4,-2,-2,1,3,-1,-3,-3,-1,-4},
					{-1,-2,-2,-1,-3,-1,-1,-2,-2,-3,-3,-1,-2,-4,7,-1,-1,-4,-3,-2,-2,-1,-2,-4},
					{1,-1,1,0,-1,0,0,0,-1,-2,-2,0,-1,-2,-1,4,1,-3,-2,-2,0,0,0,-4},
					{0,-1,0,-1,-1,-1,-1,-2,-2,-1,-1,-1,-1,-2,-1,1,5,-2,-2,0,-1,-1,0,-4},
					{-3,-3,-4,-4,-2,-2,-3,-2,-2,-3,-2,-3,-1,1,-4,-3,-2,11,2,-3,-4,-3,-2,-4},
					{-2,-2,-2,-3,-2,-1,-2,-3,2,-1,-1,-2,-1,3,-3,-2,-2,2,7,-1,-3,-2,-1,-4},
					{0,-3,-3,-3,-1,-2,-2,-3,-3,3,1,-2,1,-1,-2,-2,0,-3,-1,4,-3,-2,-1,-4},
					{-2,-1,3,4,-3,0,1,-1,0,-3,-4,0,-3,-3,-2,0,-1,-4,-3,-3,4,1,-1,-4},
					{-1,0,0,1,-3,3,4,-2,0,-3,-3,1,-1,-3,-1,0,-1,-3,-2,-2,1,4,-1,-4},
					{0,-1,-1,-1,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-2,0,0,-2,-1,-1,-1,-1,-1,-4},
					{-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,1}
					};
			
		for(String[] test : tests){
			int sc = simScore(species.get(test[0]), species.get(test[1]));
			System.out.println(test[0] + "--" + test[1] + ": " + sc);
		}
		
		}
		catch (IOException e) 
		{
			System.out.println("File I/O error!");
			e.printStackTrace();
		}
	}
	
	public static int simScore(String str1, String str2){
		
		int[][] table = new int[str1.length()+1][str2.length()+1];
		//table[0][0] = 1; // maybe wrong
		
		for(int x = 1; x<str1.length(); x++){
			table[x][0] = -4*x;
		}
		for(int y = 1; y<str2.length(); y++){
			table[0][y] = -4*y;
		}
		for(int x = 1; x<str1.length()+1; x++){
			for(int y = 1; y<str2.length()+1; y++){
				int temp1 = table[x-1][y-1] + score[scoreInd.get(str1.charAt(x-1))][scoreInd.get(str2.charAt(y-1))];
				int temp2 = table[x-1][y] - 4;
				int temp3 = table[x][y-1] - 4;
				table[x][y] = Math.max(temp1, Math.max(temp2, temp3));
			}
		}
		return table[str1.length()][str2.length()];
	}
}
