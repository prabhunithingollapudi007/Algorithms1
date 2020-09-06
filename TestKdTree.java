
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class TestKdTree {

	public static void main(String[] args) {

		/*
		 * KdTree kdtree = new KdTree(); Point2D p1 = new Point2D(0.7, 0.2); Point2D p2
		 * = new Point2D(0.5, 0.4); Point2D p3 = new Point2D(0.2, 0.3); Point2D p4 = new
		 * Point2D(0.4, 0.7); Point2D p5 = new Point2D(0.9, 0.6); Point2D p6 = new
		 * Point2D(0.9, 0.8); Point2D p7 = new Point2D(0.1, 0.9);
		 * 
		 * kdtree.insert(p1); kdtree.insert(p2); kdtree.insert(p3); kdtree.insert(p4);
		 * kdtree.insert(p5); kdtree.insert(p6); kdtree.insert(p7); for(Point2D point2d:
		 * kdtree.range(new RectHV(0, 0, 0.5, 0.5))) { System.out.println(" " +
		 * point2d); }
		 */

		String filename = args[0];
		In in = new In(filename);
		KdTree kdtree = new KdTree();
		while (!in.isEmpty()) {
			double x = in.readDouble();
			double y = in.readDouble();
			Point2D p = new Point2D(x, y);
			kdtree.insert(p);
		}
        kdtree.draw();

		/*
		 * for (Point2D point2d : kdtree.range(new RectHV(0, 0, 1, 1))) {
		 * System.out.println(" all points " + point2d); }
		 */
		//kdtree.dummy();
		for (Point2D point2d : kdtree.range(new RectHV(0, 0, 0.2, .5))) {
			System.out.println(" " + point2d);
		}

	}
}