import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

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
			System.out.print(test[0] + "--" + test[1] + ": ");
			int sc = simScore(species.get(test[0]), species.get(test[1]));
			System.out.println(sc);
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
		
		LinkedList<Alignment> aList = optAl(new Alignment("",""), str1.length(), str2.length(), table, str1, str2);
		System.out.println();
//		for(Alignment a : aList)
//		{
//			System.out.println("Score: " + a.getScore(score, scoreInd));
//			System.out.println(a + "\n");
//		}
		int ind = (int)Math.random()*aList.size();
		System.out.println("Score: " + aList.get(ind).getScore(score, scoreInd));
		System.out.println(aList.get(ind));
		System.out.println(aList.size() + " alignments");
		System.out.print ("Best score: ");
		
		return table[str1.length()][str2.length()];
	}
	
	public static LinkedList<Alignment> optAl(Alignment al, int x, int y, int[][] table, String str1, String str2)
	{
		if(x==0 && y==0)
		{
			LinkedList<Alignment> temp = new LinkedList<Alignment>();
			temp.add(al);
			return temp;
		}
		if(x==0)
		{
			al.addHead('*', str2.charAt(y-1));
			return optAl(al, x, y-1, table, str1, str2);
		}
		if(y==0)
		{
			al.addHead(str1.charAt(x-1), '*');
			return optAl(al, x-1, y, table, str1, str2);
		}
		int nbrBranching = 0;
		if(table[x-1][y-1] == table[x][y] - score[scoreInd.get(str1.charAt(x-1))][scoreInd.get(str2.charAt(y-1))]){
			nbrBranching++;
		}
		if(table[x][y-1] == table[x][y] + 4){
			nbrBranching++;
		}
		if(table[x-1][y] == table[x][y] + 4){
			nbrBranching++;
		}
		LinkedList<Alignment> temp = new LinkedList<Alignment>();
		if(table[x-1][y-1] == table[x][y] - score[scoreInd.get(str1.charAt(x-1))][scoreInd.get(str2.charAt(y-1))]){
			if(nbrBranching > 1){
				Alignment al2 = al.copy();
				al2.addHead(str1.charAt(x-1), str2.charAt(y-1));
				temp.addAll(optAl(al2, x-1, y-1, table, str1, str2));
			} else{
				al.addHead(str1.charAt(x-1), str2.charAt(y-1));
				temp.addAll(optAl(al, x-1, y-1, table, str1, str2));
			}
		}
		if(table[x][y-1] == table[x][y] + 4){
			if(nbrBranching > 1){
				Alignment al2 = al.copy();
				al2.addHead('*', str2.charAt(y-1));
				temp.addAll(optAl(al2, x, y-1, table, str1, str2));
			} else {
				al.addHead('*', str2.charAt(y-1));
				temp.addAll(optAl(al, x, y-1, table, str1, str2));
			}
		}
		if(table[x-1][y] == table[x][y] + 4){
			if(nbrBranching > 1){
				Alignment al2 = al.copy();
				al2.addHead(str1.charAt(x-1), '*');
				temp.addAll(optAl(al2, x-1, y, table, str1, str2));
			} else {
				al.addHead(str1.charAt(x-1), '*');
				temp.addAll(optAl(al, x-1, y, table, str1, str2));
			}
		}
		return temp;
	}
	
}

class Alignment 
{
	private StringBuilder sb1;
	private StringBuilder sb2;
	
	public Alignment(String s1, String s2)
	{
		sb1 = new StringBuilder(s1);
		sb2 = new StringBuilder(s2);
	}
	
	public void addHead(char c1, char c2)
	{
		sb1.append(c1);
		sb2.append(c2);
	}
	
	public String toString()
	{
		return sb1.reverse().toString() + "\n" + sb2.reverse().toString();
	}
	
	public Alignment copy(){
		return new Alignment(sb1.toString(), sb2.toString());
	}
	
	public int getScore(int[][] scoreTab, HashMap<Character,Integer> trans){
		int score = 0;
		for(int i = 0; i<sb1.length(); i++){
			score += scoreTab[trans.get(sb1.charAt(i))][trans.get(sb2.charAt(i))];
		}
		return score;
	}
}
