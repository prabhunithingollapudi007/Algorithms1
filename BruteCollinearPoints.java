
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

	private int numberOfSegments;
	private LineSegment[] lineSegments;
	private Point[] points;

	public BruteCollinearPoints(Point[] points) {
		if (points == null)
			throw new IllegalArgumentException();

		this.points = points;
		this.numberOfSegments = 0;
		this.lineSegments = null;
		checkNulls();
		Arrays.sort(points);
		this.checkDuplicates();
		this.mapPoints();
	}

	private void checkNulls() {
		for(int i = 0; i < points.length; i++) {
			if (points[i] == null)
				throw new IllegalArgumentException();
		}
	}
	
	private void checkDuplicates() {
		Point temp = points[0];
		for (int i = 1; i < points.length; i++) {
			if (points[i].compareTo(temp) == 0)
				throw new IllegalArgumentException();
			temp = points[i];
		}
	}

	private void mapPoints() {
		int n = points.length;
		ArrayList<LineSegment> al = new ArrayList<LineSegment>();
		for (int i = 0; i < n - 3; i++) {
			for (int j = i + 1; j < n - 2; j++) {
				for (int k = j + 1; k < n - 1; k++) {
					for (int l = k + 1; l < n; l++) {
						if ((points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]))
								&& (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))) {
							LineSegment temp = new LineSegment(points[i], points[l]);
							if (!al.contains(temp))
								al.add(temp);
						}
					}
				}
			}
		}
		this.numberOfSegments = al.size();
		this.lineSegments = al.toArray(new LineSegment[numberOfSegments]);
		
	}

	public int numberOfSegments() {
		return lineSegments.length;
	}

	public LineSegment[] segments() {
		return this.lineSegments;
	}

}
