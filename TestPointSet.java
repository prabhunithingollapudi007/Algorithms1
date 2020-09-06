import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class TestPointSet {
	public static void main(String[] args) {
		
		String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            brute.insert(p);
        }
        Point2D searchPoint = new Point2D(0, 0.25);
        for(Point2D point2d: brute.range(new RectHV(0, 0, 1, 1))) {
        	System.out.println(point2d);
        }
        System.out.println(brute.size());
        System.out.print(brute.nearest(searchPoint));
	}
}
