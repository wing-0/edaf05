import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class closestpair
{
	public static void main(String[] args)
	{
		try 
		{
			BufferedReader in = new BufferedReader(new FileReader(new File(args[0])));
			String currentLine;
			currentLine = in.readLine().trim();
			int dim = 0;
			
			while (!currentLine.equals("NODE_COORD_SECTION")) 
			{
				if(currentLine.startsWith("DIM"))
				{
					dim = Integer.parseInt(currentLine.split(":")[1].trim());
				}
				currentLine = in.readLine().trim();
			}
			Point[] points = new Point[dim];
			
			for(int i = 0; i < dim; i++)
			{
				currentLine = in.readLine().trim();
				String[] parts = currentLine.split("\\s+");
				points[i] = new Point(Double.parseDouble(parts[1]), 
						Double.parseDouble(parts[2]));
			}
			in.close();
//			System.out.println(Arrays.toString(points));
			double closest = closestPoints(points);
			System.out.println(closest);
			
		}
		catch (IOException e) 
		{
			System.out.println("File I/O error!");
			e.printStackTrace();
		}		
	}
	
	public static double closestPoints(Point[] points)
	{
		Point[] Px = Arrays.copyOf(points, points.length);
		Arrays.sort(Px);
		
		Tuple[] Py = new Tuple[Px.length];
		for(int i = 0; i < Px.length; i++)
		{
			Py[i] = new Tuple(i,Px[i]);
		}
		Arrays.sort(Py);
		
		return closest(Px, Py, Px.length);
	}
	
	public static double closest(Point[] Px, Tuple[] Py, int n)
	{
		if(n == 1)
		{
			return Double.MAX_VALUE;
		}
		else if(n == 2)
		{
			return Px[0].distanceTo(Px[1]);
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
				Ry[r] = t;
				r++;
			}
		}
		
		double closestL = closest(Lx, Ly, n/2);
		double closestR = closest(Rx, Ry, n-n/2);
		double delta = Double.min(closestL, closestR);
		
		// Capacity of Sy is much larger than necessary, but we don't know
		// how much "necessary" is
		ArrayList<Point> Sy = new ArrayList<Point>(Py.length);
		
		for(Tuple t : Py)
		{
			if(t.p.x > limit - delta && t.p.x < limit + delta)
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
				if(dist < delta)
				{
					delta = dist;
				}
			}
		}
		return delta;
		
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
		return Math.hypot(x-p.x, y-p.y);
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