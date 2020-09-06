import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

	private SET<Point2D> set;

	public PointSET() {
		set = new SET<Point2D>();
	}

	public boolean isEmpty() {
		return set.isEmpty();
	}

	public int size() {
		return set.size();
	}

	public void insert(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		if (!set.contains(p))
			set.add(p);
	}

	public boolean contains(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		return set.contains(p);
	}

	public void draw() {
		for (Point2D p : set) {
			StdDraw.point(p.x(), p.y());
		}
	}

	public Iterable<Point2D> range(RectHV rect) {
		if (rect == null)
			throw new IllegalArgumentException();
		ArrayList<Point2D> tempList = new ArrayList<Point2D>();
		for (Point2D point2d : set) {
			if (rect.contains(point2d))
				tempList.add(point2d);
		}

		return new PointIterator(tempList);
	}

	private class PointIterator implements Iterable<Point2D> {

		private ArrayList<Point2D> tempList;

		public PointIterator(ArrayList<Point2D> tempList) {
			this.tempList = tempList;
		}

		@Override
		public Iterator<Point2D> iterator() {
			// TODO Auto-generated method stub
			return tempList.iterator();
		}

	}

	public Point2D nearest(Point2D p) {
		if (p == null)
			throw new IllegalArgumentException();
		Point2D temp = null;
		double tempDistace = Double.POSITIVE_INFINITY;
		double currDistace;
		for (Point2D point2d : set) {
			currDistace = p.distanceSquaredTo(point2d);
			if (currDistace <= tempDistace) {
				temp = point2d;
				tempDistace = currDistace;
			}
				
		}
		return temp;
	}

	public static void main(String[] args) {

	}
}
