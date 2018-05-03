import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class closestpair
{
	static boolean isTSP = false;
	
	public static void main(String[] args)
	{
//		long time1 = System.currentTimeMillis();
		try 
		{
			
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			String currentLine;
			currentLine = in.readLine().trim();
			int dim = 0;
			Point[] points = new Point[0];
			if(!isTSP)
			{
				ArrayList<Point> ALpoints = new ArrayList<Point>();
				while(currentLine!=null){
					String[] parts = currentLine.split("\\s+");
					ALpoints.add(new Point(Double.parseDouble(parts[1]),
							Double.parseDouble(parts[2])));
				}
				points = ALpoints.toArray(new Point[0]);
				dim = points.length;
			}
			else
			{
				while (!currentLine.equals("NODE_COORD_SECTION")) 
				{
					if(currentLine.startsWith("DIM"))
					{
						dim = Integer.parseInt(currentLine.split(":")[1].trim());
					}
					currentLine = in.readLine().trim();
				}
				points = new Point[dim];

				for(int i = 0; i < dim; i++)
				{
					currentLine = in.readLine().trim();
					String[] parts = currentLine.split("\\s+");
					points[i] = new Point(Double.parseDouble(parts[1]), 
							Double.parseDouble(parts[2]));
				}
			}
			in.close();
//			System.out.println(Arrays.toString(points));
//			long time2 = System.currentTimeMillis();
			//System.out.println("Read file: " + (time2-time1));
			double[] closest = closestPoints(points);
//			long time3 = System.currentTimeMillis();
			//System.out.println("Closest: " + (time3-time2));
			System.out.println(args[0] + ": " + dim + " " + closest[0]);
			if(isTSP)
			{
				System.out.print("Between points ");
				for(Point p : points)
				{
					if(p.x == closest[1] && p.y == closest[2])
					{
						System.out.print("(" + p.x + "," + p.y + ") and ");
					}
					if(p.x == closest[3] && p.y == closest[4])
					{
						System.out.print("(" + p.x + "," + p.y + ") ");
					}
				}
				System.out.println();
			}
			
		}
		catch (IOException e) 
		{
			System.out.println("File I/O error!");
			e.printStackTrace();
		}		
	}
	
	public static double[] closestPoints(Point[] points)
	{
		Point[] Px = Arrays.copyOf(points, points.length);
		Arrays.sort(Px);
		
		Tuple[] Py = new Tuple[Px.length];
		for(int i = 0; i < Px.length; i++)
		{
			Py[i] = new Tuple(i,Px[i]);
		}
		Arrays.sort(Py);
		
		double[] result = closest(Px, Py, Px.length);
		result[0] = Math.sqrt(result[0]);
		return result;
				
	}
	
	public static double[] closest(Point[] Px, Tuple[] Py, int n)
	{
		if(n == 1)
		{
			return new double[] {Double.MAX_VALUE, -1, -1, -1, -1};
		}
		else if(n == 2)
		{
			return new double[] {Px[0].distanceTo(Px[1]), Px[0].x, 
					Px[0].y, Px[1].x, Px[1].y};
		}
		Point[] Lx = Arrays.copyOfRange(Px, 0, n/2);
		Point[] Rx = Arrays.copyOfRange(Px, n/2, n);
		
		Tuple[] Ly = new Tuple[n/2];
		Tuple[] Ry = new Tuple[n-n/2];
		
		int l = 0;
		int r = 0;
		double limit = Px[n/2].x;
		
		for(Tuple t : Py)
		{
			if(t.index < n/2)
			{
				Ly[l] = t;
				l++;
			}
			else
			{
				t.index -= n/2;
				Ry[r] = t;
				r++;
			}
		}
		
		double[] closestL = closest(Lx, Ly, n/2);
		double[] closestR = closest(Rx, Ry, n-n/2);
		double[] closestP =  closestL[0] < closestR[0] ? closestL : closestR;
		
		// Capacity of Sy is much larger than necessary, but we don't know
		// how much "necessary" is
		ArrayList<Point> Sy = new ArrayList<Point>(Py.length);
		
		for(Tuple t : Py)
		{
			if(Math.pow(t.p.x-limit,2) < closestP[0])
			{
				Sy.add(t.p);
			}
		}
		
		for(int i = 0; i < Sy.size(); i++)
		{
			for(int k = 1; k < 16; k++)
			{
				if(i+k >= Sy.size())
				{
					break;
				}
				double dist = Sy.get(i).distanceTo(Sy.get(i+k));
				if(dist < closestP[0])
				{
					closestP[0] = dist;
					closestP[1] = Sy.get(i).x;
					closestP[2] = Sy.get(i).y;
					closestP[3] = Sy.get(i).x;
					closestP[4] = Sy.get(i).y;
				}
			}
		}
		return closestP;
		
	}
}

class Tuple implements Comparable<Tuple>
{
	int index;
	Point p;
	
	public Tuple(int index, Point p)
	{
		this.index = index;
		this.p = p;
	}

	@Override
	public int compareTo(Tuple t)
	{
		return Double.compare(p.y, t.p.y);
	}
	
}

class Point implements Comparable<Point>
{
	double x;
	double y;
	
	public Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double distanceTo(Point p)
	{
		return Math.pow(x-p.x,2) + Math.pow(y-p.y,2);
	}

	@Override
	public int compareTo(Point p)
	{
		return Double.compare(x, p.x);
	}
	
	@Override
	public String toString()
	{
		return("x:" + x + ", y:" + y);
	}
}