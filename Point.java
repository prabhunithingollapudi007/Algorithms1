import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

	private final int x; // x-coordinate of this point
	private final int y; // y-coordinate of this point

	public Point(int x, int y) {
		/* DO NOT MODIFY */
		this.x = x;
		this.y = y;
	}

	public void draw() {
		/* DO NOT MODIFY */
		StdDraw.point(x, y);
	}

	public void drawTo(Point that) {
		/* DO NOT MODIFY */
		StdDraw.line(this.x, this.y, that.x, that.y);
	}

	public double slopeTo(Point that) {
		if ((this.x == that.x) && (this.y == that.y))
			return Double.NEGATIVE_INFINITY;
		if (this.x == that.x)
			return Double.POSITIVE_INFINITY;
		if (this.y == that.y)
			return +0;

		return (double) (this.y - that.y) / (double) (this.x - that.x);
	}

	public Comparator<Point> slopeOrder() {
		return new SlopeComparator();
	}

	private class SlopeComparator implements Comparator<Point> {

		@Override
		public int compare(Point o1, Point o2) {
			if (slopeTo(o1) < slopeTo(o2))
				return -1;
			if (slopeTo(o1) == slopeTo(o2))
				return 0;
			return 1;
		}

	}

	@Override
	public int compareTo(Point that) {
		if (this.y < that.y)
			return -1;
		if (this.y == that.y) {
			if (this.x < that.x)
				return -1;
			if (this.x == that.x)
				return 0;
		}
		return 1;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

	public static void main(String[] args) {
		/* YOUR CODE HERE */
	}
}
